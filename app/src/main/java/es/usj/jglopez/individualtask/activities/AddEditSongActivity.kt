package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.databinding.ActivityAddEditSongBinding
import es.usj.jglopez.individualtask.model.GenreCache
import es.usj.jglopez.individualtask.model.SingerCache
import es.usj.jglopez.individualtask.model.Song
import es.usj.jglopez.individualtask.model.SongCache

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

            if (editingSong == null) {
                // Crear nueva canción
                val newSong = Song(
                    id = System.currentTimeMillis(),
                    title = title,
                    album = album,
                    year = year,
                    runtime = runtime,
                    rating = rating,
                    votes = votes,
                    genres = listOf(selectedGenre),
                    singers = listOf(selectedSinger)
                )
                SongCache.songs = SongCache.songs + newSong
            } else {
                // Editar canción existente usando copy()
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
                SongCache.songs = SongCache.songs.map { if (it.id == updatedSong.id) updatedSong else it }
            }

            setResult(RESULT_OK)
            finish()
        }
    }
}