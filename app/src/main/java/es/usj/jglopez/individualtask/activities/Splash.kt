package es.usj.jglopez.individualtask.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.usj.jglopez.individualtask.databinding.ActivitySplashBinding
import es.usj.jglopez.individualtask.model.GenreCache
import es.usj.jglopez.individualtask.model.SingerCache
import es.usj.jglopez.individualtask.model.SongCache
import es.usj.jglopez.individualtask.network.fetchGenres
import es.usj.jglopez.individualtask.network.fetchSingers
import es.usj.jglopez.individualtask.network.fetchSongs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Splash : AppCompatActivity() {
    private val view by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

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
