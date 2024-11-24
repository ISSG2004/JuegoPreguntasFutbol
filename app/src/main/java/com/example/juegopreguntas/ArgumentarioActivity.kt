package com.example.juegopreguntas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegopreguntas.modelos.Pregunta
import com.example.recyclersqlite.R
import com.example.recyclersqlite.databinding.ActivityArgumentarioBinding
import java.io.Serializable

class ArgumentarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArgumentarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityArgumentarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        traerDatos()
    }

    private fun traerDatos() {
        val argumentario=intent.getStringExtra("ARGUMENTARIO")
        binding.tvArgumentario.text=argumentario
    }
    //añadir método que elimine la primera pregunta de la lista de preguntas generadas
    private fun borrarPregunta(): ArrayList<Pregunta> {
        val listaPreguntas = intent.getSerializableExtra("LISTAPREGUNTAS") as? ArrayList<Pregunta>
        listaPreguntas?.removeAt(0)
        return listaPreguntas ?: arrayListOf()
    }
}