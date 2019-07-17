package com.ohmyspark

import java.sql.Timestamp

import org.apache.spark.sql.{Encoder, Encoders}

object LittleHelpers extends Serializable {

  case class CheckinRaw(business_id: String, date: String)
  case class Checkin(business_id: String, date: Timestamp)
  case class UserFriendRaw(user_id: String, friends: String)
  case class UserFriend(user_id: String, friend_id: String)

  implicit val checkinRawEncoder: Encoder[CheckinRaw] =
    Encoders.product[CheckinRaw]
  implicit val checkinEncoder: Encoder[Checkin] =
    Encoders.product[Checkin]
  implicit val userFriendRawEncoder: Encoder[UserFriendRaw] =
    Encoders.product[UserFriendRaw]
  implicit val userFriendEncoder: Encoder[UserFriend] =
    Encoders.product[UserFriend]

}
