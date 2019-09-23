package com.hyunki.geniusplazacodingchallenge.view.rv

import androidx.recyclerview.widget.DiffUtil
import com.hyunki.geniusplazacodingchallenge.model.User

class UserDiffUtilCallBack: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem.id == newItem.id
}