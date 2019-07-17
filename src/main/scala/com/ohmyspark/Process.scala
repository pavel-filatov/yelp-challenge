package com.ohmyspark

import java.sql.Timestamp

import com.ohmyspark.LittleHelpers._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, regexp_replace, split, struct, when}
import org.apache.spark.sql.types.{ArrayType, IntegerType}

object Process {

  def processCheckin()(df: DataFrame): DataFrame =
    df.as[CheckinRaw]
      .flatMap { r =>
        r.date
          .split(',')
          .map { s =>
            LittleHelpers.Checkin(r.business_id, Timestamp.valueOf(s.trim))
          }
      }
      .toDF()

  def processFriend()(df: DataFrame): DataFrame =
    df.as[UserFriendRaw]
      .flatMap { r =>
        r.friends
          .split(',')
          .map { s =>
            UserFriend(r.user_id, s.trim)
          }
      }
      .toDF()

  def processBusiness()(df: DataFrame): DataFrame =
    df.withColumn(
        "hours",
        struct(
          col("hours.Monday").as("monday"),
          col("hours.Tuesday").as("tuesday"),
          col("hours.Wednesday").as("wednesday"),
          col("hours.Thursday").as("thursday"),
          col("hours.Friday").as("friday"),
          col("hours.Saturday").as("saturday"),
          col("hours.Sunday").as("sunday")
        )
      )
      .withColumn("categories",
                  split(regexp_replace(col("categories"), ",\\s+", ","), ","))

  def processBusinessAttributes()(df: DataFrame): DataFrame =
    df.select("business_id", "attributes.*")

  def processUser()(df: DataFrame): DataFrame =
    df.withColumn("elite",
                  when(col("elite") =!= "",
                       split(regexp_replace(col("elite"), ",\\s+", ","), ","))
                    .cast(ArrayType(IntegerType)))

}
