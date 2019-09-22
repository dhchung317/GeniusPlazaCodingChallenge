package com.hyunki.geniusplazacodingchallenge

import androidx.recyclerview.widget.DiffUtil

class UserDiffUtilCallBack: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem.id == newItem.id
}