package com.github.jirobird.roomexp.domain.usecases

import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.data.repository.IMeetingsAndUsersRepository

class InsertUser(private val repository: IMeetingsAndUsersRepository) {
    suspend operator fun invoke(entity: UserEntity) {
        repository.insertUser(entity)
    }
}