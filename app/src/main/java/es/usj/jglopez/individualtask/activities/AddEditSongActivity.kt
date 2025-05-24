package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.usj.jglopez.individualtask.databinding.ActivityAddEditSongBinding
import es.usj.jglopez.individualtask.model.GenreCache
import es.usj.jglopez.individualtask.model.SingerCache
import es.usj.jglopez.individualtask.model.Song
import es.usj.jglopez.individualtask.model.SongCache
import es.usj.jglopez.individualtask.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditSongActivity : AppCompatActivity() {
    private val view by lazy { ActivityAddEditSongBinding.inflate(layoutInflater) }
    private var editingSong: Song? = null
    private lateinit var genres: List<es.usj.jglopez.individualtask.model.Genre>
    private lateinit var singers: List<es.usj.jglopez.individualtask.model.Singer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        // Recuperar géneros y cantantes de caché
        genres = GenreCache.genres
        singers = SingerCache.singers

        // Adaptadores para los Spinners
        val genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres.map { it.name })
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerGenres.adapter = genreAdapter

        val singerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, singers.map { it.name })
        singerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerSingers.adapter = singerAdapter

        // Si viene song_id, estamos editando
        val songId = intent.getLongExtra("song_id", -1)
        if (songId != -1L) {
            editingSong = SongCache.songs.find { it.id == songId }
            editingSong?.let { song ->
                view.etTitle.setText(song.title)
                view.etAlbum.setText(song.album)
                view.etYear.setText(song.year.toString())
                view.etRuntime.setText(song.runtime.toString())
                view.etRating.setText(song.rating.toString())
                view.etVotes.setText(song.votes.toString())
                view.spinnerGenres.setSelection(genres.indexOfFirst { it.id == song.genres.firstOrNull()?.id })
                view.spinnerSingers.setSelection(singers.indexOfFirst { it.id == song.singers.firstOrNull()?.id })
            }
        }

        view.btnSave.setOnClickListener {
            val title = view.etTitle.text.toString().trim()
            val album = view.etAlbum.text.toString().trim()
            val year = view.etYear.text.toString().toIntOrNull() ?: 0
            val runtime = view.etRuntime.text.toString().toIntOrNull() ?: 0
            val rating = view.etRating.text.toString().toIntOrNull() ?: 0
            val votes = view.etVotes.text.toString().toLongOrNull() ?: 0

            val selectedGenre = genres[view.spinnerGenres.selectedItemPosition]
            val selectedSinger = singers[view.spinnerSingers.selectedItemPosition]

            if (title.isEmpty() || album.isEmpty()) {
                Toast.makeText(this, "Title and album required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val success = withContext(Dispatchers.IO) {
                    if (editingSong == null) {
                        // POST (crear)
                        val newSong = Song(
                            id = 0, // El servidor asigna el ID
                            title = title,
                            album = album,
                            year = year,
                            runtime = runtime,
                            rating = rating,
                            votes = votes,
                            genres = listOf(selectedGenre),
                            singers = listOf(selectedSinger)
                        )
                        postSong(newSong)
                    } else {
                        // PUT (editar)
                        val updatedSong = editingSong!!.copy(
                            title = title,
                            album = album,
                            year = year,
                            runtime = runtime,
                            rating = rating,
                            votes = votes,
                            genres = listOf(selectedGenre),
                            singers = listOf(selectedSinger)
                        )
                        putSong(updatedSong)
                    }
                }
                if (success) {
                    // Recarga la lista desde la API y actualiza el caché local
                    val updatedList = withContext(Dispatchers.IO) { fetchSongs() }
                    SongCache.songs = updatedList
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@AddEditSongActivity, "Error saving song", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}