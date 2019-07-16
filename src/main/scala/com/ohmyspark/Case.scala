package com.ohmyspark

import java.sql.Timestamp

object Case extends Serializable {

  case class CheckinRaw(business_id: String, date: String)
  case class Checkin(business_id: String, date: Timestamp)

  case class UserFriendRaw(user_id: String, friends: String)
  case class UserFriend(user_id: String, friend_id: String)

}
