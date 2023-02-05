package com.github.jirobird.roomexp.domain.usecases

import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository

class InsertMeetingWithUsers(private val repository: IMeetingsAndUsersRepository) {
    suspend operator fun invoke(entity: MeetingEntity, userList: List<UserEntity>) {
        repository.insertMeetingWithUsers(entity, userList)
    }
}