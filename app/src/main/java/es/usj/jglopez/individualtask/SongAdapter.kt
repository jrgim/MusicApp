package es.usj.jglopez.individualtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class SongAdapter(context: Context, songs: List<Song>) :
    ArrayAdapter<Song>(context, 0, songs) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_song, parent, false)

        val song = getItem(position)!!

        val titleText = itemView.findViewById<TextView>(R.id.textViewTitle)
        val albumText = itemView.findViewById<TextView>(R.id.textViewAlbum)
        val yearText = itemView.findViewById<TextView>(R.id.textViewYear)
        val favoriteOff = itemView.findViewById<ImageView>(R.id.favoriteOff)
        val favoriteOn = itemView.findViewById<ImageView>(R.id.favoriteOn)

        titleText.text = song.title
        albumText.text = song.album
        yearText.text = song.year.toString()

        // Mostrar solo el icono correspondiente
        if (song.isFavorite) {
            favoriteOn.visibility = View.VISIBLE
            favoriteOff.visibility = View.GONE
        } else {
            favoriteOn.visibility = View.GONE
            favoriteOff.visibility = View.VISIBLE
        }

        // Listener para marcar como favorito
        favoriteOff.setOnClickListener {
            song.isFavorite = true
            notifyDataSetChanged()
        }

        // Listener para quitar de favoritos
        favoriteOn.setOnClickListener {
            song.isFavorite = false
            notifyDataSetChanged()
        }

        return itemView
    }
}
