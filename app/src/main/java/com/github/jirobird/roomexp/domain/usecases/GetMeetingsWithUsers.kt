package com.github.jirobird.roomexp.domain.usecases

import com.github.jirobird.roomexp.data.db.cross.MeetingWithUsers
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository
import kotlinx.coroutines.flow.Flow

class GetMeetingsWithUsers(private val repository: IMeetingsAndUsersRepository) {
    operator fun invoke(): Flow<List<MeetingWithUsers>> {
        return repository.allMeetingsWithUsers()
    }
}