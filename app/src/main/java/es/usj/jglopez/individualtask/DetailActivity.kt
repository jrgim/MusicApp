package es.usj.jglopez.individualtask

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            findViewById<TextView>(R.id.textViewSingers).text = "Singers: ${song.singers.joinToString(", ")}"
            findViewById<TextView>(R.id.textViewGenres).text = "Genres: ${song.genres.joinToString(", ")}"
        } else {
            findViewById<TextView>(R.id.textViewTitle).text = "Song not found"
        }
    }
}