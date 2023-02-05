package com.github.jirobird.roomexp.ui.user.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.roomexp.data.db.user.UserEntity
import com.github.jirobird.roomexp.databinding.VhUserBinding

class UserListAdapter(private val listener: IUserListAdapterSelectListener?): RecyclerView.Adapter<UserViewHolder>() {
    interface IUserListAdapterSelectListener {
        fun onItemSelected(selectedUserList: List<UserEntity>)
    }

    private var _users:List<UserEntity>? = null
    private val selectedUserList = ArrayList<UserEntity>()

    fun pushItems(users:List<UserEntity>?) {
        _users = users
        notifyDataSetChanged()
    }

    fun clearSelectedUser() {
        selectedUserList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.context, VhUserBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val entity = _users?.get(position)

        holder.binding.tvUserName.text = _users?.get(position)?.userName ?: "пустое имя"
        holder.binding.tvUserUuid.text = _users?.get(position)?.userId ?: "нет id"
        holder.binding.root.setBackgroundColor(Color.TRANSPARENT)
        holder.binding.root.layoutParams =  LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        entity?.let {
            if (selectedUserList.contains(it)) {
                holder.binding.root.setBackgroundColor(Color.BLUE)
            }
        }

        if (listener != null) {
            holder.binding.root.setOnClickListener {
                _users?.get(position)?.let {
                    toggleSelection(it, position)
                }
            }
        }
    }

    private fun toggleSelection(userEntity: UserEntity, position: Int) {
        if (selectedUserList.contains(userEntity)) {
            selectedUserList.remove(userEntity)
        } else {
            selectedUserList.add(userEntity)
        }

        notifyItemChanged(position)
        listener?.onItemSelected(selectedUserList)
    }

    override fun getItemCount(): Int {
        return if(_users == null) {
            0
        } else {
            _users!!.size
        }
    }
}