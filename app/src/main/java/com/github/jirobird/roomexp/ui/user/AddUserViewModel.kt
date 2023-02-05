package com.github.jirobird.roomexp.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.domain.usecases.MeetingsAndUsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(private val meetingsAndUsersUseCases: MeetingsAndUsersUseCases
):ViewModel() {
    private val supervisorJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + supervisorJob)

    private val _userListState = MutableStateFlow<List<UserEntity>?>(null)
    val userListState = _userListState.asStateFlow()

    init {
        loadAllUsers()
    }

    private fun loadAllUsers() {
        viewModelScope.launch {
            meetingsAndUsersUseCases.getAllUsers().onEach {
                _userListState.emit(it)
            }.launchIn(ioScope)
        }
    }

    fun pushUserToDatabase(name:String) {
        viewModelScope.launch {
            ioScope.launch {
                val user = UserEntity(userName = name)
                meetingsAndUsersUseCases.insertUser(user)
                viewModelScope.launch {
                    loadAllUsers()
                }
            }
        }
    }
}