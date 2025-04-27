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

        // Datos de ejemplo
        val songs = listOf(
            Song(1, "Song A", "Album X", 2020, 210, 4.5, 1000),
            Song(2, "Song B", "Album Y", 2021, 180, 4.2, 800)
        )

        val adapter = SongAdapter(this, songs)
        listView.adapter = adapter
    }
}