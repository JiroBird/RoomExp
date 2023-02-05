package com.github.jirobird.roomexp.ui.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.roomexp.data.db.cross.MeetingWithUsers
import com.github.jirobird.roomexp.databinding.VhMeetingBinding

class MeetingListAdapter:RecyclerView.Adapter<MeetingViewHolder>() {
    private var _meetings:List<MeetingWithUsers>? = null

    fun fillAdapter(meetings:List<MeetingWithUsers>?) {
        _meetings = meetings
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        return MeetingViewHolder(VhMeetingBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        _meetings?.get(position)?.let {
            holder.binding.tvMeetingName.text = it.meeting.meetingName
            holder.binding.tvMeetingUuid.text = it.meeting.meetingId
            holder.binding.tvUsersCome.text = "человеков: ${it.users.size}"
        }
    }

    override fun getItemCount(): Int {
        return _meetings?.size ?: 0
    }
}