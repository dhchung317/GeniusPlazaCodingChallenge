package com.hyunki.geniusplazacodingchallenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyunki.geniusplazacodingchallenge.R
import com.hyunki.geniusplazacodingchallenge.view.rv.UserAdapter
import com.hyunki.geniusplazacodingchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private val adapter = UserAdapter()
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun initializeList() {
        binding.userRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        list = binding.userRecyclerView
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.goto_add_user_button -> startActivityForResult(Intent(this,
                AddUserActivity::class.java),0)
        }
    }

}
