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

        lifecycleScope.launch {
            // 1. Cargar géneros y cantantes primero y guardarlos en cache
            GenreCache.genres = withContext(Dispatchers.IO) { fetchGenres() }
            SingerCache.singers = withContext(Dispatchers.IO) { fetchSingers() }

            // 2. Ahora cargar canciones usando los caches
            SongCache.songs = withContext(Dispatchers.IO) { fetchSongs() }

            // 3. Lanza la MainActivity
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }
    }
}
