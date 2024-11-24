package com.example.juegopreguntas

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegopreguntas.modelos.Pregunta
import com.example.recyclersqlite.R
import com.example.recyclersqlite.databinding.ActivityPreguntaBinding

class PreguntaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreguntaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityPreguntaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recogerDatos()
        binding.pbTiempoRestante.max=45
        binding.pbTiempoRestante.max
        cuentaAtras()
        enviarDatos()

    }
    //Crear un método para llevar la logica de que siempre no salga la primera pregunta como verdadera
    private fun ordenPreguntas() {

    }

    //Añadir un contador para controlar el numero de pregunta
    private fun enviarDatos() {
        val listaPreguntas = intent.getSerializableExtra("PREGUNTAS") as? ArrayList<Pregunta>
        val argumentario:String = listaPreguntas!![0].argumentario
        val intent=Intent(this, ArgumentarioActivity::class.java)
        intent.putExtra("ARGUMENTARIO",argumentario)
        intent.putExtra("LISTAPREGUNTAS",ArrayList(listaPreguntas))
        binding.btnCorregir.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }

    private fun recogerDatos(){
        val listaPreguntas = intent.getSerializableExtra("PREGUNTAS") as? ArrayList<Pregunta>
        if (listaPreguntas != null && listaPreguntas.isNotEmpty()) {
            binding.tvPregunta.text = listaPreguntas[0].enunciadoPregunta
            ordenPreguntas()
            binding.btnRespuesta1.text = listaPreguntas[0].respuestaVerdadera
            binding.btnRespuesta2.text = listaPreguntas[0].respuestaFalsa
        } else {
            binding.tvPregunta.text = "No hay preguntas disponibles"
            binding.btnRespuesta1.text = ""
            binding.btnRespuesta2.text = ""
        }
    }



    fun cuentaAtras() {
        val totalTime = 45L // Total de 45 segundos
        val timer = object : CountDownTimer(totalTime * 1000, 1000) { // 45 segundos, intervalos de 1 segundo
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000

                binding.pbTiempoRestante.progress = secondsLeft.toInt() //actualizar el progreso de la progress bar

                //Actualizar la etiqueta que informa del tiempo
                binding.tvTiempo.text = "$secondsLeft s" //
            }

            override fun onFinish() {
                binding.pbTiempoRestante.progress = binding.pbTiempoRestante.min //cuando el tiempo acabe la barra llegara al final
                binding.btnRespuesta1.setEnabled(false)
                binding.btnRespuesta2.setEnabled(false)
            }
        }
        timer.start()
    }
}