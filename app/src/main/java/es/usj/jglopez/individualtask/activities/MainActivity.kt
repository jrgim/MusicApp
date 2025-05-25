package es.usj.jglopez.individualtask.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.adapters.SongAdapter
import es.usj.jglopez.individualtask.databinding.ActivityMainBinding
import es.usj.jglopez.individualtask.model.Song
import es.usj.jglopez.individualtask.model.SongCache

class MainActivity : AppCompatActivity() {

    private val adapter = SongAdapter()
    private val view by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var showingFavorites = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        // Inicializa la lista
        adapter.updateList(SongCache.songs)
        view.lvViewSongs.adapter = adapter

        // Click normal: ver detalle
        view.lvViewSongs.setOnItemClickListener { _, _, position, _ ->
            val song = adapter.getItem(position)
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("song_id", song.id)
            startActivity(intent)
        }

        // Long click: editar
        view.lvViewSongs.setOnItemLongClickListener { _, _, position, _ ->
            val song = adapter.getItem(position)
            val intent = Intent(this, AddEditSongActivity::class.java)
            intent.putExtra("song_id", song.id)
            startActivity(intent)
            true
        }

        // Botón contacto
        view.buttonContact.setOnClickListener {
            startActivity(Intent(this, Contact::class.java))
        }

        // Botón add/edit
        view.buttonAddEdit.setOnClickListener {
            startActivity(Intent(this, AddEditSongActivity::class.java))
        }

        // Botón favoritos
        view.buttonToggleFavorites.setOnClickListener {
            showingFavorites = !showingFavorites
            updateSongList()
            view.buttonToggleFavorites.text = if (showingFavorites) "Show All" else "Show Favorites"
        }

        // Botón buscar
        view.btnSearch.setOnClickListener {
            val query = view.etSearch.text.toString().trim()
            val filtered = if (query.isEmpty()) {
                getCurrentSongList()
            } else {
                getCurrentSongList().filter { it.title.contains(query, ignoreCase = true) }
            }
            adapter.updateList(filtered)
        }
    }

    override fun onResume() {
        super.onResume()
        updateSongList()
    }

    private fun getCurrentSongList(): List<Song> =
        if (showingFavorites) SongCache.songs.filter { it.isFavorite }
        else SongCache.songs

    private fun updateSongList() {
        adapter.updateList(getCurrentSongList())
    }
}
