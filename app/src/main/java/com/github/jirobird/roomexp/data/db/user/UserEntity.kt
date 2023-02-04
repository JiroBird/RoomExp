package com.github.jirobird.roomexp.data.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserEntity(
    @PrimaryKey
    val userId:String = UUID.randomUUID().toString(),
    val userName: String
)