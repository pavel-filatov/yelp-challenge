package com.ohmyspark

import java.sql.Timestamp

import com.datastax.spark.connector._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.{col, split}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, SparkSession}
import Case.UserFriend
import Case.UserFriendRaw

object ReaderApp extends ReaderApp {

  Logger.getLogger("org").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .getOrCreate()

    val runner = new ReaderApp

    runner.run(spark)
  }

}

class ReaderApp {

  implicit val checkinRawEncoder: Encoder[Case.CheckinRaw] =
    Encoders.product[Case.CheckinRaw]
  implicit val checkinEncoder: Encoder[Case.Checkin] =
    Encoders.product[Case.Checkin]
  implicit val userFriendRawEncoder: Encoder[UserFriendRaw] =
    Encoders.product[UserFriendRaw]
  implicit val userFriendEncoder: Encoder[UserFriend] =
    Encoders.product[UserFriend]

  def run(implicit spark: SparkSession): Unit = {
//    val review: DataFrame =
//      readFileWithSchema("data_samples/review.json", Schema.reviewSchema)
//    val tip: DataFrame =
//      readFileWithSchema("data_samples/tip.json", Schema.tipSchema)
//    val photo: DataFrame =
//      readFileWithSchema("data/photo.json", Schema.photoSchema)
//    val checkin: DataFrame =
//      readFileWithSchema("data_samples/checkin.json", Schema.checkinSchema)
//        .transform(processCheckin())
//    val user: DataFrame =
//      readFileWithSchema("data_samples/user.json", Schema.userSchema)
//        .transform(splitElite("elite"))
    val friend: Dataset[UserFriend] =
      readFileWithSchema("data_samples/user.json", Schema.friendSchema)
        .transform(processFriend())

   friend.rdd.saveToCassandra("yelp", "friend")
  }

  def processCheckin()(df: DataFrame): DataFrame =
    df.as[Case.CheckinRaw]
      .flatMap { r =>
        r.date
          .split(',')
          .map { s =>
            Case.Checkin(r.business_id, Timestamp.valueOf(s.trim))
          }
      }
      .toDF()

  def processFriend()(df: DataFrame): Dataset[UserFriend] =
    df.as[UserFriendRaw]
    .flatMap { r =>
      r.friends
        .split(',')
        .map { s =>
          UserFriend(r.user_id, s.trim)
        }
    }

  def splitElite(eliteColName: String)(df: DataFrame): DataFrame =
    df.withColumn(eliteColName,
                  split(col(eliteColName), ",").cast("array<int>"))

  def readFileWithSchema(jsonPath: String, schema: StructType)(
      implicit spark: SparkSession): DataFrame =
    spark.read.schema(schema).json(jsonPath)
}
