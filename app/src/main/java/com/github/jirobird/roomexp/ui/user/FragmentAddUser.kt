package com.github.jirobird.roomexp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jirobird.roomexp.R
import com.github.jirobird.roomexp.databinding.FragmentAddUserBinding
import com.github.jirobird.roomexp.ui.main.MainFragment
import com.github.jirobird.roomexp.ui.user.view.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentAddUser: Fragment() {
    companion object {
        fun newInstance() = FragmentAddUser()
    }

    private val viewModel by viewModels<AddUserViewModer>()
    private lateinit var binder: FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentAddUserBinding.inflate(inflater)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binder.mbDismissFragment.setOnClickListener {
            closeFragment()
        }

        binder.mbSaveUser.setOnClickListener {
            val textValue = binder.tietNameInput.text?.toString()
            textValue?.let {
                viewModel.pushUserToDatabase(it)
                binder.tietNameInput.text = null
            }
        }

        binder.rvUserList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binder.rvUserList.adapter = UserListAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.userListState.collect { userList ->
                    (binder.rvUserList.adapter as UserListAdapter).pushItems(userList)
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
}