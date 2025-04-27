package es.usj.jglopez.individualtask

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Carga datos en background
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // TODO: Aquí carga los datos del webservice y/o cache
                delay(2000) // Simula la carga, elimina cuando tengas el webservice
            }
            // Cuando termina la carga, lanza la siguiente actividad y elimina el Splash del back stack
            val intent = Intent(this@Splash, MainActivity::class.java)
            startActivity(intent)
            finish() // Elimina SplashActivity del back stack
        }
    }
}