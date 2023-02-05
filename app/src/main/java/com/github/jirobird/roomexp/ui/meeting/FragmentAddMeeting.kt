package com.github.jirobird.roomexp.ui.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jirobird.roomexp.R
import com.github.jirobird.roomexp.data.db.meeting.MeetingEntity
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.databinding.FragmentAddMeetingBinding
import com.github.jirobird.roomexp.ui.main.MainFragment
import com.github.jirobird.roomexp.ui.user.view.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentAddMeeting: Fragment(), UserListAdapter.IUserListAdapterSelectListener {
    companion object {
        fun newInstance() = FragmentAddMeeting()
    }
    private lateinit var binding:FragmentAddMeetingBinding
    private val viewModel by viewModels<AddMeetingViewModel>()
    private lateinit var _selectedUsers:List<UserEntity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMeetingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbDismissFragment.setOnClickListener {
            closeFragment()
        }

        binding.mbSaveMeeting.setOnClickListener {
            //TODO: надо переносить логику проверки в ViewModel, сейчас плевать
            if(binding.tietNameInput.text == null || binding.tietNameInput.text.toString().isEmpty()) {
                Toast.makeText(view.context, "Имя события обязательно", Toast.LENGTH_SHORT).show()
            } else {
                if (!this::_selectedUsers.isInitialized) {
                    _selectedUsers = ArrayList()
                }
                viewModel.pushMeetingToDatabase( MeetingEntity(meetingName = binding.tietNameInput.text.toString()), _selectedUsers)
                binding.tietNameInput.text = null
                (binding.rvUserList.adapter as UserListAdapter).clearSelectedUser()
            }
        }

        binding.rvUserList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.rvUserList.adapter = UserListAdapter(this)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.userListState.collect { userList ->
                        (binding.rvUserList.adapter as UserListAdapter).pushItems(userList)
                    }
                }
                launch {
                    viewModel.uiEvent.collectLatest {
                        when(it) {
                            MeetingViewModelDatabaseEvents.MeetingViewModelDatabaseEventsWillSave -> {
                                binding.mbSaveMeeting.isEnabled = false
                            }

                            MeetingViewModelDatabaseEvents.MeetingViewModelDatabaseEventsSaved -> {
                                closeFragment()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun closeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MainFragment.newInstance(),
                MainFragment::class.java.simpleName
            )
            .commitNow()
    }

    override fun onItemSelected(selectedUserList: List<UserEntity>) {
        _selectedUsers = selectedUserList
    }
}