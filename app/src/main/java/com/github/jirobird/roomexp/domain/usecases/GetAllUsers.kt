package com.github.jirobird.roomexp.domain.usecases

import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsers(private val repository: IMeetingsAndUsersRepository) {
    operator fun invoke():Flow<List<UserEntity>?> {
        return repository.allUser()
    }
}