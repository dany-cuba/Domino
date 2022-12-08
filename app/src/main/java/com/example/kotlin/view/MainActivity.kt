package com.example.kotlin.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.databinding.ActivityMainBinding
import com.example.kotlin.model.CountRepository
import com.example.kotlin.viewmodel.PhotoViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      set random image on start
        photoViewModel.randomPhoto()


        binding.bJugar.setOnClickListener {
            startActivity(Intent(this, Jugar::class.java))
            Toast.makeText(this, "¡A Jugar!", Toast.LENGTH_SHORT).show()
        }

        photoViewModel.photoModel.observe(this) {
            Picasso
                .get()
                .load(it.resImage)
                .into(binding.ivPhoto)
        }
        binding.photoContainer.setOnClickListener{
            photoViewModel.randomPhoto()
        }
    }

    override fun onRestart() {
        super.onRestart()
        photoViewModel.randomPhoto()
    }

    private fun cambiarMarcadorAzul() {
        binding.tEquipoAzul.text = CountRepository.teamA.toString()

    }

    private fun cambiarMarcadorRojo() {
        binding.tEquipoRojo.text = CountRepository.teamB.toString()
    }

    override fun onResume() {
        super.onResume()
        cambiarMarcadorRojo()
        cambiarMarcadorAzul()
    }
}