package com.github.jirobird.roomexp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jirobird.roomexp.data.db.cross.MeetingWithUsers
import com.github.jirobird.roomexp.domain.usecases.MeetingsAndUsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val meetingsAndUsersUseCases: MeetingsAndUsersUseCases
) : ViewModel() {
    private val supervisorJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + supervisorJob)

    private var getAllItemsJob: Job? = null


    private val _meetingListState = MutableStateFlow<List<MeetingWithUsers>?>(null)
    val meetingListState = _meetingListState.asStateFlow()

    init {
        fillMockData()
        loadMeetings()
    }

    private fun fillMockData() {
        viewModelScope.launch {
            ioScope.launch {
                try {
                    val count = meetingsAndUsersUseCases.getMeetingsCount()

                    if (count == 0) {
                        Log.d(MainViewModel::class.java.simpleName,"хуй")
                    } else {
                        Log.d(MainViewModel::class.java.simpleName,"$count")
                    }
                } catch (e:Exception) {

                }
            }
        }
    }

    private fun loadMeetings() {
        getAllItemsJob?.cancel()
        getAllItemsJob = meetingsAndUsersUseCases.getMeetingsWithUsers().onEach {
            _meetingListState.emit(it)
        }.launchIn(viewModelScope)
    }
}