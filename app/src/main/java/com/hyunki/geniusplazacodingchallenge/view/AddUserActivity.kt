package com.hyunki.geniusplazacodingchallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunki.geniusplazacodingchallenge.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
