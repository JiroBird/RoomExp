package com.github.jirobird.roomexp.data.db.cross

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingAndUsersDao {

    @Query("SELECT COUNT(meetingId) FROM MeetingEntity")
    fun getCount():Int

    @Transaction
    @Query("SELECT * FROM MeetingEntity")
    fun getMeetingWithUsers():Flow<List<MeetingWithUsers>>

    @Transaction
    @Query("SELECT * FROM UserEntity")
    fun getUsersWithMeetings():Flow<List<UserWithMeetings>>
}