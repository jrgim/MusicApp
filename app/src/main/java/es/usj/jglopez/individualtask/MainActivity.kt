package es.usj.jglopez.individualtask

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        listView.setOnItemClickListener { _, _, position, _ ->
            val song = songs[position] // Or from your adapter
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("song_id", song.id)
            startActivity(intent)
        }

        val buttonContact = findViewById<Button>(R.id.buttonContact)
        buttonContact.setOnClickListener {
            val intent = Intent(this, contact::class.java)
            startActivity(intent)
        }
        val adapter = SongAdapter(this, songs)
        listView.adapter = adapter
    }
}