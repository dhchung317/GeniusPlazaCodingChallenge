package com.hyunki.geniusplazacodingchallenge.view.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyunki.geniusplazacodingchallenge.databinding.UserItemBinding
import com.hyunki.geniusplazacodingchallenge.model.User
import com.squareup.picasso.Picasso

class UserAdapter: PagedListAdapter<User, UserAdapter.UserViewHolder>(
    UserDiffUtilCallBack()
) {
    private lateinit var binding: UserItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let(holder::onBind)
    }
    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val image: ImageView = binding.userImageView
        private val email: TextView = binding.userEmailTextView
        private val name: TextView = binding.userNameTextView

        fun onBind(user: User){
            Log.d("name rv",user.first_name)
            if(user.avatar != null) {
                Picasso.get().load(user.avatar).into(image)
            }
            email.text = user.email
            name.text = "${user.first_name} ${user.last_name}"
        }
    }
}