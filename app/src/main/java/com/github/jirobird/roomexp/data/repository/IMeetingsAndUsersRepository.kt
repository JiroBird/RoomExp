package com.github.jirobird.roomexp.data.repository

import com.github.jirobird.roomexp.data.db.cross.MeetingWithUsers
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface IMeetingsAndUsersRepository {
    suspend fun getMeetingsCount():Int
    suspend fun pushMeeting(entity:MeetingWithUsers)


    suspend fun insertMeetingWithUsers(entity: MeetingEntity, usersList: List<UserEntity>)
    fun allMeetingsWithUsers():Flow<List<MeetingWithUsers>>

    fun allUser():Flow<List<UserEntity>?>
    suspend fun insertUser(entity: UserEntity)

}