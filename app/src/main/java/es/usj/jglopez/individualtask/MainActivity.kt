package es.usj.jglopez.individualtask

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.lvViewSongs)
        val songs = SongCache.songs // Recupera las canciones cargadas en Splash

        val adapter = SongAdapter(this, songs)
        listView.adapter = adapter
    }
}