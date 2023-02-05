package com.github.jirobird.roomexp.domain.repository

import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersCrossDao
import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersCrossRef
import com.github.jirobird.roomexp.data.db.cross.MeetingAndUsersDao
import com.github.jirobird.roomexp.data.db.cross.MeetingWithUsers
import com.github.jirobird.roomexp.data.db.meeting.MeetingDao
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserDao
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MeetingsAndUsersRepositoryImpl @Inject constructor(
    private var meetingDao: MeetingDao,
    private val userDao: UserDao,
    private val crossMeetingAndUsersDao: MeetingAndUsersCrossDao,
    private val meetingAndUsersDao: MeetingAndUsersDao
):IMeetingsAndUsersRepository {
    override suspend fun getMeetingsCount(): Int {
        return meetingDao.getMeetingsCount()
    }

    override suspend fun pushMeeting(entity: MeetingWithUsers) {

    }

    override suspend fun insertMeetingWithUsers(
        entity: MeetingEntity,
        usersList: List<UserEntity>
    ) {
        meetingDao.insertMeeting(entity)
        usersList.forEach {
            crossMeetingAndUsersDao.insertMeetingWithUsers(MeetingAndUsersCrossRef(entity.meetingId, it.userId))
        }
    }

    override fun allMeetingsWithUsers(): Flow<List<MeetingWithUsers>> {
        return meetingAndUsersDao.getMeetingWithUsers()
    }

    override fun allUser(): Flow<List<UserEntity>?> {
        return userDao.getAllUser()
    }

    override suspend fun insertUser(entity: UserEntity) {
        userDao.insertUser(entity)
    }
}