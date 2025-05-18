package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.R
import es.usj.jglopez.individualtask.model.SongCache

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val songId = intent.getLongExtra("song_id", -1)
        val song = SongCache.songs.find { it.id == songId }

        if (song != null) {
            findViewById<TextView>(R.id.textViewTitle).text = song.title
            findViewById<TextView>(R.id.textViewAlbum).text = "Album: ${song.album}"
            findViewById<TextView>(R.id.textViewYear).text = "Year: ${song.year}"
            findViewById<TextView>(R.id.textViewRuntime).text = "Runtime: ${song.runtime} sec"
            findViewById<TextView>(R.id.textViewRating).text = "Rating: ${song.rating}"
            findViewById<TextView>(R.id.textViewVotes).text = "Votes: ${song.votes}"
            val textViewFavorite = findViewById<TextView>(R.id.textViewFavorite)
            if (song.isFavorite) {
                textViewFavorite.text = "The song is favorite"
            } else {
                textViewFavorite.text = "" // Establece una cadena vacía si no es favorita
            }
            val singersText = if (song.singers.isNotEmpty())
                song.singers.joinToString(", ") { it.name }
            else "No singers"
            val genresText = if (song.genres.isNotEmpty())
                song.genres.joinToString(", ") { it.name }
            else "No genres"
            findViewById<TextView>(R.id.textViewSingers).text = "Singers: $singersText"
            findViewById<TextView>(R.id.textViewGenres).text = "Genres: $genresText"
        } else {
            findViewById<TextView>(R.id.textViewTitle).text = "Song not found"
        }
    }
}