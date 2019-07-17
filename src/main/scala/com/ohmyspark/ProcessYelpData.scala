package com.ohmyspark

import java.nio.file.Path

import com.ohmyspark.Process._
import com.ohmyspark.Utils.{applyTransformations, getAbsolutePath, readFileWithSchema, saveIntoCassandraKeyspace}
import org.apache.spark.sql._
import org.apache.spark.sql.types.StructType

object ProcessYelpData extends ProcessYelpData {

  def main(args: Array[String]): Unit = {

    val datadir: String = args.find(_.startsWith("-datadir=")) match {
      case Some(wdArg) => wdArg.replace("-datadir=", "")
      case _ => throw new Exception("`-datadir` argument should be provided. Use: `-datadir=</directory/where/data/stored>`")
    }

    val spark = SparkSession
      .builder()
      .master("local[4]")
      .getOrCreate()

    val runner = new ProcessYelpData

    runner.run(datadir = datadir, spark = spark)
  }

}

class ProcessYelpData {

  type DataFrameName = String
  type Filename = String

  def run(datadir: String, spark: SparkSession): Unit = {

    val dataPath: Path = getAbsolutePath(datadir)

    val rawDataFrames: Map[DataFrameName, DataFrame] =
      namesFilesAndSchemas mapValues { case (filename: Filename, schema: StructType) =>
        readFileWithSchema(rootDir = dataPath, spark = spark)(filename, schema)
      }

    val finalDataFrames: Map[DataFrameName, DataFrame] =
      rawDataFrames map {
        case (name: DataFrameName, df: DataFrame) =>
          val trans = transformations.getOrElse(name, List())
          (name, applyTransformations(df, trans))
      }

    finalDataFrames foreach { case (table, df) =>
      saveIntoCassandraKeyspace("yelp")(df, table)
    }
  }

  lazy val namesFilesAndSchemas: Map[DataFrameName, (Filename, StructType)] = Map(
    "review" -> ("review.json", Schema.reviewSchema),
    "tip" -> ("tip.json", Schema.tipSchema),
    "photo" -> ("photo.json", Schema.photoSchema),
    "checkin" -> ("checkin.json", Schema.checkinSchema),
    "user" -> ("user.json", Schema.userSchema),
    "friend" -> ("user.json", Schema.friendSchema),
    "business" -> ("business.json", Schema.businessSchema),
    "business_attributes" -> ("business.json", Schema.businessAttributesSchema)
  )

  lazy val transformations: Map[DataFrameName, List[DataFrame => DataFrame]] = Map(
    "checkin" -> List(processCheckin()),
    "user" -> List(processUser()),
    "friend" -> List(processFriend()),
    "business" -> List(processBusiness()),
    "business_attributes" -> List(processBusinessAttributes())
  )

}
