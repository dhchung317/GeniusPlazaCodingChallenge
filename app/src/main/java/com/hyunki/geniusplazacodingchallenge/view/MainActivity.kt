package com.hyunki.geniusplazacodingchallenge.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyunki.geniusplazacodingchallenge.R
import com.hyunki.geniusplazacodingchallenge.databinding.ActivityMainBinding
import com.hyunki.geniusplazacodingchallenge.view.rv.UserAdapter
import com.hyunki.geniusplazacodingchallenge.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private val adapter = UserAdapter()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var list: RecyclerView
    private lateinit var dataObserver: RecyclerView.AdapterDataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeList()

        dataObserver = getDataObserver()
        adapter.registerAdapterDataObserver(dataObserver)
        viewModel.getLiveData().observeForever(Observer{
                list -> adapter.submitList(list)
            dataObserver.onItemRangeInserted(0,6)
        })

        binding.gotoAddUserButton.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            viewModel.getUsersFromDatabase()
            viewModel.getLiveData().observe(this, Observer{
                    list->adapter.submitList(list)
            })
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.goto_add_user_button -> startActivityForResult(Intent(this, AddUserActivity::class.java),0)
        }
    }

    private fun initializeList() {
        binding.userRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        list = binding.userRecyclerView
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
    }

    private fun getDataObserver(): RecyclerView.AdapterDataObserver {
        return object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                for(u in viewModel.getLiveData().value!!){
                        viewModel.addUserToDatabase(u)
                }
            }
        }
    }
}
