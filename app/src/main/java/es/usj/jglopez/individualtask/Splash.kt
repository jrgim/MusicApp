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

        // Esto asegura que la red se hace fuera del main thread
        lifecycleScope.launch {
            val songs = withContext(Dispatchers.IO) {
                fetchSongs() // Función de red
            }
            SongCache.songs = songs
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }
    }
}