package com.ohmyspark

import java.nio.file.{Path, Paths}

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.cassandra._

object Utils {

  def getAbsolutePath(dir: String) : Path = Paths.get(dir).toAbsolutePath()

  def readFileWithSchema(rootDir: Path, spark: SparkSession)
                        (jsonPath: String, schema: StructType): DataFrame =
    spark.read.schema(schema).json(rootDir.resolve(jsonPath).toString)

  def saveIntoCassandraKeyspace(keyspace: String)
                               (df: DataFrame, tableName: String): Unit =
    df.write
      .cassandraFormat(table = tableName, keyspace = keyspace)
      .mode("append")
      .save()

  def applyTransformations(df: DataFrame, transformations: List[DataFrame => DataFrame]): DataFrame =
    transformations match {
      case Nil => df
      case t :: ts => applyTransformations(df.transform(t), ts)
    }

}
