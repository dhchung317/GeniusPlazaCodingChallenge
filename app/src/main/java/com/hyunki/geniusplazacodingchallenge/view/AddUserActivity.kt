package com.hyunki.geniusplazacodingchallenge.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.hyunki.geniusplazacodingchallenge.R
import com.hyunki.geniusplazacodingchallenge.databinding.ActivityAddUserBinding
import com.hyunki.geniusplazacodingchallenge.network.UserService
import com.hyunki.geniusplazacodingchallenge.viewmodel.UserViewModel
import io.reactivex.rxkotlin.subscribeBy

class AddUserActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var service: UserService
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        service = UserService.getInstance()!!

        binding.addUserButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.add_user_button ->
                if(
                    binding.addUserFirstNameEditText.text.isEmpty()
                    || binding.addUserLastNameEditText.text.isEmpty()
                    || binding.addUserEmailEditText.text.isEmpty()
                ){
                    Toast.makeText(this,"fields cannot be empty", Toast.LENGTH_SHORT).show()
                }else {
                    service.postUser(
                            binding.addUserFirstNameEditText.text.toString(),
                            binding.addUserLastNameEditText.text.toString(),
                            binding.addUserEmailEditText.text.toString()
                    ).subscribeBy(
                        onError = {throwable -> Log.d("error add user post", throwable.toString()) },
                        onSuccess = {user -> Log.d("on success post", user.first_name) }
                    )

                    service.getUsers(1).subscribeBy(
                        onSuccess = {userResponse -> Log.d("on success get", userResponse.data[0].first_name) }
                    )
                    setIntent()
                }
        }
    }

    private fun setIntent(){
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
    }
}
