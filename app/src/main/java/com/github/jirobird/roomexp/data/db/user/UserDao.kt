package com.github.jirobird.roomexp.data.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): Flow<List<UserEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(entity: UserEntity)
}