package es.usj.jglopez.individualtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class SongAdapter(context: Context, songs: List<Song>) :
    ArrayAdapter<Song>(context, 0, songs) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        }

        val song = getItem(position)

        val titleText = itemView!!.findViewById<TextView>(R.id.textViewTitle)
        val albumText = itemView.findViewById<TextView>(R.id.textViewAlbum)
        val yearText = itemView.findViewById<TextView>(R.id.textViewYear)

        titleText.text = song?.title
        albumText.text = song?.album
        yearText.text = song?.year.toString()

        return itemView
    }
}