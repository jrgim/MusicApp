package es.usj.jglopez.individualtask.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.databinding.ActivityDetailBinding
import es.usj.jglopez.individualtask.model.SongCache

class DetailActivity : AppCompatActivity() {
    private val view by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        val songId = intent.getLongExtra("song_id", -1)
        val song = SongCache.songs.find { it.id == songId }

        if (song != null) {
            view.textViewTitle.text = song.title
            view.textViewAlbum.text = "Album: ${song.album}"
            view.textViewYear.text = "Year: ${song.year}"
            view.textViewRuntime.text = "Runtime: ${song.runtime} sec"
            view.textViewRating.text = "Rating: ${song.rating}"
            view.textViewVotes.text = "Votes: ${song.votes}"

            view.textViewFavorite.text = if (song.isFavorite) "The song is favorite" else ""

            val singersText = if (song.singers.isNotEmpty())
                song.singers.joinToString(", ") { it.name }
            else "No singers"
            val genresText = if (song.genres.isNotEmpty())
                song.genres.joinToString(", ") { it.name }
            else "No genres"

            view.textViewSingers.text = "Singers: $singersText"
            view.textViewGenres.text = "Genres: $genresText"
        } else {
            view.textViewTitle.text = "Song not found"
        }
    }
}
