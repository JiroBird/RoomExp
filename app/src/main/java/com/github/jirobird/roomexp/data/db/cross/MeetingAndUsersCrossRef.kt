package com.github.jirobird.roomexp.data.db.cross

import androidx.room.Entity

@Entity(primaryKeys = ["meetingId", "userId"])
data class MeetingAndUsersCrossRef(
    val meetingId:String,
    val userId:String)