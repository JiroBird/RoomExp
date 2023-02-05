package com.github.jirobird.roomexp.ui.meeting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.domain.usecases.MeetingsAndUsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMeetingViewModel@Inject constructor(private val meetingsAndUsersUseCases: MeetingsAndUsersUseCases
): ViewModel() {
    private val supervisorJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + supervisorJob)

    private val _userListState = MutableStateFlow<List<UserEntity>?>(null)
    val userListState = _userListState.asStateFlow()

    private val _uiEvent = Channel<MeetingViewModelDatabaseEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var chachedList:ArrayList<UserEntity> = ArrayList()

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

    fun pushMeetingToDatabase(meetingEntity: MeetingEntity, userList:List<UserEntity>) {
        chachedList.clear()
        chachedList.addAll(userList)

        viewModelScope.launch {
            _uiEvent.send(MeetingViewModelDatabaseEvents.MeetingViewModelDatabaseEventsWillSave)
            ioScope.launch {
                meetingsAndUsersUseCases.insertMeetingsAndUsers(meetingEntity, chachedList)
                viewModelScope.launch {
                    _uiEvent.send(MeetingViewModelDatabaseEvents.MeetingViewModelDatabaseEventsSaved)
                }
            }
        }
    }
}