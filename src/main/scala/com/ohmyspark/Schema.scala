package com.ohmyspark

import org.apache.spark.sql.types._

object Schema {

  val reviewSchema: StructType = new StructType()
    .add("business_id", StringType)
    .add("cool", LongType)
    .add("date", TimestampType)
    .add("funny", LongType)
    .add("review_id", StringType)
    .add("stars", DoubleType)
    .add("text", StringType)
    .add("useful", LongType)
    .add("user_id", StringType)

  val tipSchema: StructType = new StructType()
    .add("business_id", StringType)
    .add("compliment_count", LongType)
    .add("date", TimestampType)
    .add("text", StringType)
    .add("user_id", StringType)

  val photoSchema: StructType = new StructType()
    .add("caption", StringType)
    .add("photo_id", StringType)
    .add("business_id", StringType)
    .add("label", StringType)

  val checkinSchema: StructType = new StructType()
    .add("business_id", StringType)
    .add("date", StringType)

  val userSchema: StructType = new StructType()
    .add("user_id", StringType)
    .add("name", StringType)
    .add("review_count", LongType)
    .add("yelping_since", TimestampType)
    .add("useful", LongType)
    .add("funny", LongType)
    .add("cool", LongType)
    .add("elite", StringType)
    .add("fans", LongType)
    .add("average_stars", DoubleType)
    .add("compliment_hot", LongType)
    .add("compliment_more", LongType)
    .add("compliment_profile", LongType)
    .add("compliment_cute", LongType)
    .add("compliment_list", LongType)
    .add("compliment_note", LongType)
    .add("compliment_plain", LongType)
    .add("compliment_cool", LongType)
    .add("compliment_funny", LongType)
    .add("compliment_writer", LongType)
    .add("compliment_photos", LongType)

  val friendSchema: StructType = new StructType()
    .add("user_id", StringType)
    .add("friends", StringType)

  val businessSchema: StructType = new StructType()

}
