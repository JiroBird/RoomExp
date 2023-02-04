package com.github.jirobird.roomexp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.jirobird.roomexp.R
import com.github.jirobird.roomexp.databinding.FragmentMainBinding
import com.github.jirobird.roomexp.ui.user.FragmentAddUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private var isPanelOpened = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.meetingListState.collect { meetingList ->
                }
            }
        }

        binding.fabAddAll.setOnClickListener {
            togglePanel()
        }

        binding.fabAddEvent.setOnClickListener {
            dismissPanel()
        }

        binding.fabAddUser.setOnClickListener {
            dismissPanel()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentAddUser.newInstance(), FragmentAddUser::class.java.simpleName)
                .commitNow()
        }
    }

    private fun togglePanel(){
        if (isPanelOpened) {
            dismissPanel()
        } else {
            presentPanel()
        }
    }

    private fun dismissPanel() {
        isPanelOpened = false

        binding.fabAddEvent.animate().translationY(0f).start()
        binding.fabAddUser.animate().translationY(0f).start()
        binding.fabAddEvent.animate().alpha(0f).start()
        binding.fabAddUser.animate().alpha(0f).start()
    }

    private fun presentPanel() {
        isPanelOpened = true
        binding.fabAddEvent.animate()
            .translationY(-resources.getDimension(R.dimen.offset_add_event)).start()
        binding.fabAddUser.animate().translationY(-resources.getDimension(R.dimen.offset_add_user))
            .start()
        binding.fabAddEvent.animate().alpha(1f).start()
        binding.fabAddUser.animate().alpha(1f).start()
    }
}