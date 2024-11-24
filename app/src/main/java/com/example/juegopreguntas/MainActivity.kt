package com.example.juegopreguntas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegopreguntas.bbdd.Crud
import com.example.juegopreguntas.modelos.Pregunta
import com.example.recyclersqlite.R
import com.example.recyclersqlite.databinding.ActivityMainBinding
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listaPreguntas=mutableListOf<Pregunta>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        crearEventos()
        }

    private fun crearEventos() {
        val intent = Intent(this, PreguntaActivity::class.java)
        intent.putExtra("PREGUNTAS" , ArrayList(generarPreguntasJuego()))
        binding.btnInicioJuego.setOnClickListener {
            startActivity(intent) // Iniciar la nueva actividad
            finish()
        }
    }

    private fun generarPreguntasJuego():MutableList<Pregunta>{
        listaPreguntas=Crud().read()
        var lista10Preguntas= mutableListOf<Pregunta>()
        var listaIndicesPreguntas=mutableSetOf<Int>()
        while (listaIndicesPreguntas.size < 10) {
            listaIndicesPreguntas.add(generarNumeroAleatorio())
        }
        val indicesPreguntasList = listaIndicesPreguntas.toList()
        for (i in 0 until 10) {
            val preguntaIndex = indicesPreguntasList[i]
            // Asegurarte de que el índice esté dentro del rango de listaPreguntas
            if (preguntaIndex >= 0 && preguntaIndex < listaPreguntas.size) {
                lista10Preguntas.add(listaPreguntas[preguntaIndex])

            }
        }
        return lista10Preguntas
    }

    fun generarNumeroAleatorio(): Int {
        return Random.nextInt(50)
    }

}
