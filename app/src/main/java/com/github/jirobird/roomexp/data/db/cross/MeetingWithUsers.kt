package com.github.jirobird.roomexp.data.db.cross

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserEntity

data class MeetingWithUsers(
    @Embedded
    val meeting:MeetingEntity,

    @Relation(parentColumn = "meetingId", entityColumn = "userId", associateBy = Junction(MeetingAndUsersCrossRef::class))
    val users:List<UserEntity>
)

data class UserWithMeetings(
    @Embedded
    val user:UserEntity,

    @Relation(parentColumn = "userId", entityColumn = "meetingId", associateBy = Junction(MeetingAndUsersCrossRef::class))
    val meetings:List<MeetingEntity>
)
