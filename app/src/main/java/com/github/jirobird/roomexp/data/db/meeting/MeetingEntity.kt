package com.github.jirobird.roomexp.data.db.meeting

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class MeetingEntity(
    @PrimaryKey
    val meetingId:String = UUID.randomUUID().toString(),
    val meetingName:String,
)
