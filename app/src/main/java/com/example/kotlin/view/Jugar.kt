package com.example.kotlin.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.databinding.ActivityJugarBinding
import com.example.kotlin.model.CountRepository

class Jugar : AppCompatActivity() {

    private lateinit var binding: ActivityJugarBinding

    private var jugadas = ArrayList<Pair<Int, Int>>()
    private var status = ArrayList<String>()

    //variables para contar puntos
    private var puntos: Int = 0
    private var puntos2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actualizarVista(1)
        actualizarVista(2)

        initUi()
    }

    private fun initUi() = binding.run{
        //Botones
        undoBut.setOnClickListener {
            if (jugadas.isNotEmpty()) {
                undoPoints(jugadas.last().first, -jugadas.last().second)
                jugadas.removeLast()
                status.removeLast()
                Log.d("MY FUCKING TAG", status.toString())
                if (status.isNotEmpty()) {
                    restablecerUndo(status.last().toString())
                } else {
                    restablecerUndo("on")
                }
            }
        }
        //Botones del Equipo 1
        bPase.setOnClickListener {
            puntos += 10
            jugadas.add(1 to 10)
            actualizarVista(1)
            desactivarPases("pass")
        }

        bAdd.setOnClickListener {
            if (etPuntos.text.toString() == "") {
                Toast.makeText(this@Jugar, "XD", Toast.LENGTH_SHORT).show()
            } else {
                updPuntos(1, etPuntos.text.toString().toInt())
            }
        }
        bPaseMano.setOnClickListener {
            puntos += 20
            jugadas.add(1 to 20)
            actualizarVista(1)
            desactivarPases("pass")
        }

        //Botones equipo 2
        bPase2.setOnClickListener {
            puntos2 += 10
            jugadas.add(2 to 10)
            actualizarVista(2)
            desactivarPases("pass")
        }
        bAdd2.setOnClickListener {
            if (etPuntos2.text.toString() == "") {
                Toast.makeText(this@Jugar, "XD", Toast.LENGTH_SHORT).show()
            } else {
                updPuntos(2, etPuntos2.text.toString().toInt())
            }
        }
        bPaseMano2.setOnClickListener {
            puntos2 += 20
            jugadas.add(2 to 20)
            actualizarVista(2)
            desactivarPases("pass")
        }
    }
    //Funciones del Equipo 1

    private fun actualizarVista(jugador: Int) {
        when (jugador) {
            1 -> binding.tPuntosTotales.text = puntos.toString()
            2 -> binding.tPuntosTotales2.text = puntos2.toString()
        }
    }

    //Metodo para revizar cuando un equipo gana
    private fun checkWin() {
        if (puntos >= 150) {
            Toast.makeText(this, "Ganó el Equipo Azul!", Toast.LENGTH_SHORT).show()
            CountRepository.teamA++
            binding.bPase.isEnabled = false
            binding.bAdd.isEnabled = false
            binding.bPaseMano.isEnabled = false
            finish()
        }else if(puntos2 >= 150){
            Toast.makeText(this, "Ganó el Equipo Rojo!", Toast.LENGTH_SHORT).show()
            CountRepository.teamB++
            binding.bPase.isEnabled = false
            binding.bAdd.isEnabled = false
            binding.bPaseMano.isEnabled = false
            finish()
        }
    }

    private fun updPuntos(equipo: Int, p: Int) {
        if (binding.checkBoxPegue.isChecked && !binding.checkBoxPegue2.isChecked) {
            val add = 10 + p
            when (equipo) {
                1 -> puntos += add
                2 -> puntos2 += add
            }
            jugadas.add(equipo to add)
        } else if(!binding.checkBoxPegue.isChecked && binding.checkBoxPegue2.isChecked){
            val add = 10 + p
            when (equipo) {
                1 -> puntos += add
                2 -> puntos2 += add
            }
            jugadas.add(equipo to add)
        }else{
            when (equipo) {
                1 -> puntos += p
                2 -> puntos2 += p
            }

            jugadas.add(equipo to p)
        }
        desactivarPases("add")
        reiniciar(equipo)
        actualizarVista(equipo)
        checkWin()
    }

    private fun desactivarPases(tipo:String) {

        var cantTrue = 0
        if (puntos >= 130 || puntos2 >= 130) {
            binding.bPaseMano.isEnabled = false
            binding.bPaseMano2.isEnabled = false
            cantTrue++
            if (puntos >= 140 || puntos2 >= 140) {
                cantTrue++
            }
        }
        when (cantTrue) {
            0 -> {
                when(tipo){
                    "pass" -> deactivatePass()
                    "add" -> activatePass()
                }
            }
            1 -> {
                status.add("middle")
            }
            2 -> {
                deactivatePass()
            }
        }
    }
    private fun deactivatePass(){
        binding.bPaseMano.isEnabled = false
        binding.bPaseMano2.isEnabled = false
        binding.bPase.isEnabled = false
        binding.bPase2.isEnabled = false
        status.add("off")
    }
    private fun activatePass(){
        binding.bPaseMano.isEnabled = true
        binding.bPaseMano2.isEnabled = true
        binding.bPase.isEnabled = true
        binding.bPase2.isEnabled = true
        status.add("on")
    }

    private fun reiniciar(jugador: Int) {
        when(jugador){
            1 -> {binding.etPuntos.setText("")
                binding.checkBoxPegue.isChecked = false}

            2 -> {binding.etPuntos2.setText("")
                binding.checkBoxPegue2.isChecked = false}
        }

    }

    private fun undoPoints(jugador:Int, p:Int){
        when (jugador) {
            1 -> puntos += p
            2 -> puntos2 += p

        }
        reiniciar(jugador)
        actualizarVista(jugador)
    }

    private fun restablecerUndo(tipo:String){
        when(tipo){
            "on" -> {
                binding.bPaseMano.isEnabled = true
                binding.bPaseMano2.isEnabled = true
                binding.bPase.isEnabled = true
                binding.bPase2.isEnabled = true
            }
            "off" -> {
                binding.bPaseMano.isEnabled = false
                binding.bPaseMano2.isEnabled = false
                binding.bPase.isEnabled = false
                binding.bPase2.isEnabled = false
            }
            "middle" -> {
                binding.bPaseMano.isEnabled = false
                binding.bPaseMano2.isEnabled = false
                binding.bPase.isEnabled = true
                binding.bPase2.isEnabled = true
            }
        }
    }
}
