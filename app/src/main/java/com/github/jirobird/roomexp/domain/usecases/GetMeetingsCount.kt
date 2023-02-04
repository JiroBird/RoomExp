package com.github.jirobird.roomexp.domain.usecases

import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository

class GetMeetingsCount(private val repository: IMeetingsAndUsersRepository) {
    suspend operator fun invoke():Int {
        return repository.getMeetingsCount()
    }
}