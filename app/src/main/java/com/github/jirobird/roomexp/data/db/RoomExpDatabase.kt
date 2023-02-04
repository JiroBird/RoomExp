package com.github.jirobird.roomexp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersCrossDao
import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersCrossRef
import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersDao
import com.github.jirobird.roomexp.data.db.meeting.MeetingDao
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserDao
import com.github.jirobird.roomexp.data.db.user.UserEntity

@Database(
    entities = [MeetingEntity::class, UserEntity::class, MeetingAndUsersCrossRef::class],
    version = 1,
    exportSchema = false
)

abstract class RoomExpDatabase:RoomDatabase() {
    abstract val meetingDao:MeetingDao
    abstract val userDao:UserDao
    abstract val meetingAndUserDao: MeetingAndUsersDao
    abstract val crossMeetingAndUsersDao: MeetingAndUsersCrossDao

    companion object {
        const val DATABASE_NAME = "room_exp_application_database"
    }
}