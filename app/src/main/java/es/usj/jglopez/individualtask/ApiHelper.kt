package es.usj.jglopez.individualtask

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

fun fetchSongs(): List<Song> {
    val url = URL("http://10.0.2.2:8080/songs")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    val songs = mutableListOf<Song>()

    try {
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                // singers and genres are parse to array
                val singersJson = obj.getJSONArray("singers")
                val singers = mutableListOf<Int>()
                for (j in 0 until singersJson.length()) {
                    singers.add(singersJson.getInt(j))
                }

                val genresJson = obj.getJSONArray("genres")
                val genres = mutableListOf<Int>()
                for (j in 0 until genresJson.length()) {
                    genres.add(genresJson.getInt(j))
                }

                val song = Song(
                    id = obj.getLong("id"),
                    title = obj.getString("title"),
                    album = obj.getString("album"),
                    year = obj.getInt("year"),
                    runtime = obj.getInt("runtime"),
                    rating = obj.getInt("rating"),
                    votes = obj.getLong("votes"),
                    singers = singers,
                    genres = genres
                )
                songs.add(song)
            }
        }
    } finally {
        connection.disconnect()
    }
    return songs
}