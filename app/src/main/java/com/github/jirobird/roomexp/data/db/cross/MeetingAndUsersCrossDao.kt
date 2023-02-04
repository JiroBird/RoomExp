package com.github.jirobird.roomexp.data.db.cross

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingAndUsersCrossDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetingWithUsers(entity: MeetingAndUsersCrossRef)

    @Transaction
    @Query("SELECT * FROM MeetingAndUsersCrossRef")
    fun getMeetingWithAllUsers():Flow<List<MeetingAndUsersCrossRef>>
}