package es.usj.jglopez.individualtask.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.R
import es.usj.jglopez.individualtask.model.Song
import es.usj.jglopez.individualtask.adapters.SongAdapter
import es.usj.jglopez.individualtask.model.SongCache

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SongAdapter
    private lateinit var allSongs: List<Song>
    private lateinit var listView: ListView
    private lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvViewSongs)
        editTextSearch = findViewById(R.id.etSearch)
        val buttonSearch = findViewById<Button>(R.id.btnSearch)
        val buttonContact = findViewById<Button>(R.id.buttonContact)
        val buttonAddEdit = findViewById<Button>(R.id.buttonAddEdit)

        allSongs = SongCache.songs
        adapter = SongAdapter(this, allSongs)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val song = adapter.getItem(position)!!
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("song_id", song.id)
            startActivity(intent)
        }

        buttonContact.setOnClickListener {
            val intent = Intent(this, contact::class.java)
            startActivity(intent)
        }

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            val filtered = if (query.isEmpty()) {
                allSongs
            } else {
                allSongs.filter { it.title.contains(query, ignoreCase = true) }
            }
            adapter = SongAdapter(this, filtered)
            listView.adapter = adapter
        }

        buttonAddEdit.setOnClickListener {
            val intent = Intent(this, AddEditSongActivity::class.java)
            startActivity(intent)
        }
        listView.setOnItemLongClickListener { _, _, position, _ ->
            val song = adapter.getItem(position)!!
            val intent = Intent(this, AddEditSongActivity::class.java)
            intent.putExtra("song_id", song.id)
            startActivity(intent)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza allSongs y el adapter cada vez que vuelves a MainActivity
        allSongs = SongCache.songs
        adapter = SongAdapter(this, allSongs)
        listView.adapter = adapter
    }
}