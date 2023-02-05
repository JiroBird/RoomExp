package com.github.jirobird.roomexp.data.db.meeting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingDao {
    @Query("SELECT COUNT(meetingId) FROM MeetingEntity")
    fun getMeetingsCount():Int

    @Query("SELECT * FROM MeetingEntity")
    fun getAllMeetings():Flow<List<MeetingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(entity: MeetingEntity)
}