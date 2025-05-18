package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.model.Genre
import es.usj.jglopez.individualtask.model.GenreCache
import es.usj.jglopez.individualtask.R
import es.usj.jglopez.individualtask.model.Singer
import es.usj.jglopez.individualtask.model.SingerCache
import es.usj.jglopez.individualtask.model.Song
import es.usj.jglopez.individualtask.model.SongCache

class AddEditSongActivity : AppCompatActivity() {
    private var editingSong: Song? = null
    private lateinit var genres: List<Genre>
    private lateinit var singers: List<Singer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_song)

        // Recuperar géneros y cantantes de caché
        genres = GenreCache.genres
        singers = SingerCache.singers

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etAlbum = findViewById<EditText>(R.id.etAlbum)
        val etYear = findViewById<EditText>(R.id.etYear)
        val etRuntime = findViewById<EditText>(R.id.etRuntime)
        val etRating = findViewById<EditText>(R.id.etRating)
        val etVotes = findViewById<EditText>(R.id.etVotes)
        val spinnerGenres = findViewById<Spinner>(R.id.spinnerGenres)
        val spinnerSingers = findViewById<Spinner>(R.id.spinnerSingers)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Llenar los Spinners con los nombres
        spinnerGenres.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres.map { it.name })
        spinnerSingers.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, singers.map { it.name })

        // Si viene song_id, estamos editando
        val songId = intent.getLongExtra("song_id", -1)
        if (songId != -1L) {
            editingSong = SongCache.songs.find { it.id == songId }
            editingSong?.let { song ->
                etTitle.setText(song.title)
                etAlbum.setText(song.album)
                etYear.setText(song.year.toString())
                etRuntime.setText(song.runtime.toString())
                etRating.setText(song.rating.toString())
                etVotes.setText(song.votes.toString())
                spinnerGenres.setSelection(genres.indexOfFirst { it.id == song.genres.firstOrNull()?.id })
                spinnerSingers.setSelection(singers.indexOfFirst { it.id == song.singers.firstOrNull()?.id })
            }
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val album = etAlbum.text.toString().trim()
            val year = etYear.text.toString().toIntOrNull() ?: 0
            val runtime = etRuntime.text.toString().toIntOrNull() ?: 0
            val rating = etRating.text.toString().toIntOrNull() ?: 0
            val votes = etVotes.text.toString().toLongOrNull() ?: 0

            val selectedGenre = genres[spinnerGenres.selectedItemPosition]
            val selectedSinger = singers[spinnerSingers.selectedItemPosition]

            if (title.isEmpty() || album.isEmpty()) {
                Toast.makeText(this, "Title and album required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editingSong == null) {
                // Crear nueva canción
                val newSong = Song(
                    id = System.currentTimeMillis(), // O usa el ID que devuelva la API
                    title = title,
                    album = album,
                    year = year,
                    runtime = runtime,
                    rating = rating,
                    votes = votes,
                    genres = listOf(selectedGenre),
                    singers = listOf(selectedSinger)
                )
                // Añade a SongCache.songs si es local
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
                // Reemplaza la canción en SongCache
                SongCache.songs = SongCache.songs.map { if (it.id == updatedSong.id) updatedSong else it }
            }

            setResult(RESULT_OK)
            finish()
        }
    }
}
