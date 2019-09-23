package com.hyunki.geniusplazacodingchallenge.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.hyunki.geniusplazacodingchallenge.R
import com.hyunki.geniusplazacodingchallenge.databinding.ActivityAddUserBinding
import com.hyunki.geniusplazacodingchallenge.model.PostUser
import com.hyunki.geniusplazacodingchallenge.network.UserService
import com.hyunki.geniusplazacodingchallenge.viewmodel.UserViewModel

class AddUserActivity : AppCompatActivity(), View.OnClickListener {
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
        when (v?.id) {
            R.id.add_user_button ->
                if (
                    binding.addUserFirstNameEditText.text.isEmpty()
                    || binding.addUserLastNameEditText.text.isEmpty()
                    || binding.addUserEmailEditText.text.isEmpty()
                ) {
                    Toast.makeText(applicationContext, "fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.postUser(
                        PostUser(
                            binding.addUserFirstNameEditText.text.toString(),
                            binding.addUserLastNameEditText.text.toString(),
                            binding.addUserEmailEditText.text.toString()
                        )
                    )
                    Toast.makeText(applicationContext, "user added", Toast.LENGTH_SHORT).show()
                    setIntent()
                }
        }
    }

    private fun setIntent() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
    }
}
