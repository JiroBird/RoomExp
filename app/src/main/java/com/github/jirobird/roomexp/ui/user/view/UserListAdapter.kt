package com.github.jirobird.roomexp.ui.user.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.databinding.VhUserBinding

class UserListAdapter: RecyclerView.Adapter<UserViewHolder>() {

    private var _users:List<UserEntity>? = null

    fun pushItems(users:List<UserEntity>?) {
        _users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.context, VhUserBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.tvUserName.text = _users?.get(position)?.userName ?: "пустое имя"
        holder.binding.tvUserUuid.text = _users?.get(position)?.userId ?: "нет id"

    }

    override fun getItemCount(): Int {
        return if(_users == null) {
            0
        } else {
            _users!!.size
        }
    }
}