package com.github.jirobird.roomexp.di

import android.app.Application
import androidx.room.Room
import com.github.jirobird.roomexp.data.db.RoomExpDatabase
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository
import com.github.jirobird.roomexp.domain.repository.MeetingsAndUsersRepositoryImpl
import com.github.jirobird.roomexp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomExpAppModule {

    @Provides
    @Singleton
    fun provideRoomExpDatabase(app:Application):RoomExpDatabase {
        return Room.databaseBuilder(app, RoomExpDatabase::class.java, RoomExpDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMeetingAndUserRepository(roomExpDatabase: RoomExpDatabase):IMeetingsAndUsersRepository {
        return MeetingsAndUsersRepositoryImpl(
            roomExpDatabase.meetingDao,
            roomExpDatabase.userDao,
            roomExpDatabase.crossMeetingAndUsersDao,
            roomExpDatabase.meetingAndUserDao)
    }

    @Provides
    @Singleton
    fun provideMeetingAndUsersUseCases(repository: IMeetingsAndUsersRepository):MeetingsAndUsersUseCases {
        return MeetingsAndUsersUseCases(
            getMeetingsCount = GetMeetingsCount(repository),
            getMeetingsWithUsers = GetMeetingsWithUsers(repository),
            insertUser = InsertUser(repository),
            getAllUsers = GetAllUsers(repository)
        )
    }
}