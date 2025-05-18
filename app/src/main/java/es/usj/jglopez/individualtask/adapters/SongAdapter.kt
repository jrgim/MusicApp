package es.usj.jglopez.individualtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import es.usj.jglopez.individualtask.R
import es.usj.jglopez.individualtask.model.Song

class SongAdapter : BaseAdapter() {

    private var songs: List<Song> = emptyList()

    fun updateList(newList: List<Song>) {
        songs = newList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = songs.size

    override fun getItem(position: Int): Song = songs[position]

    override fun getItemId(position: Int): Long = songs[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holderView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            holderView = LayoutInflater.from(parent?.context).inflate(R.layout.item_song, parent, false)
            viewHolder = ViewHolder(
                holderView.findViewById(R.id.textViewTitle),
                holderView.findViewById(R.id.textViewAlbum),
                holderView.findViewById(R.id.textViewYear),
                holderView.findViewById(R.id.favoriteOff),
                holderView.findViewById(R.id.favoriteOn)
            )
            holderView.tag = viewHolder
        } else {
            holderView = convertView
            viewHolder = holderView.tag as ViewHolder
        }

        val song = getItem(position)
        viewHolder.title.text = song.title
        viewHolder.album.text = song.album
        viewHolder.year.text = song.year.toString()

        // Favoritos: muestra solo uno de los dos corazones
        if (song.isFavorite) {
            viewHolder.favoriteOn.visibility = View.VISIBLE
            viewHolder.favoriteOff.visibility = View.GONE
        } else {
            viewHolder.favoriteOn.visibility = View.GONE
            viewHolder.favoriteOff.visibility = View.VISIBLE
        }

        viewHolder.favoriteOff.setOnClickListener {
            song.isFavorite = true
            notifyDataSetChanged()
        }
        viewHolder.favoriteOn.setOnClickListener {
            song.isFavorite = false
            notifyDataSetChanged()
        }

        return holderView
    }

    data class ViewHolder(
        val title: TextView,
        val album: TextView,
        val year: TextView,
        val favoriteOff: ImageView,
        val favoriteOn: ImageView
    )
}
