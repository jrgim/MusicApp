package es.usj.jglopez.individualtask

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

fun fetchSingers(): List<Singer> {
    val url = URL("http://10.0.2.2:8080/singers")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    val singers = mutableListOf<Singer>()
    try {
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val singer = Singer(
                    id = obj.getLong("id"),
                    name = obj.getString("name")
                )
                singers.add(singer)
            }
        }
    } finally {
        connection.disconnect()
    }
    return singers
}

fun fetchGenres(): List<Genre> {
    val url = URL("http://10.0.2.2:8080/genres")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    val genres = mutableListOf<Genre>()
    try {
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val genre = Genre(
                    id = obj.getLong("id"),
                    name = obj.getString("name")
                )
                genres.add(genre)
            }
        }
    } finally {
        connection.disconnect()
    }
    return genres
}

// Ahora fetchSongs usa los caches existentes para mapear los IDs a objetos
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

                // singers y genres como listas de IDs
                val singersJson = obj.getJSONArray("singers")
                val singerList = mutableListOf<Singer>()
                for (j in 0 until singersJson.length()) {
                    val singerId = singersJson.getLong(j)
                    // Busca el objeto Singer por ID en SingerCache
                    SingerCache.singers.find { it.id == singerId }?.let { singerList.add(it) }
                }

                val genresJson = obj.getJSONArray("genres")
                val genreList = mutableListOf<Genre>()
                for (j in 0 until genresJson.length()) {
                    val genreId = genresJson.getLong(j)
                    // Busca el objeto Genre por ID en GenreCache
                    GenreCache.genres.find { it.id == genreId }?.let { genreList.add(it) }
                }

                val song = Song(
                    id = obj.getLong("id"),
                    title = obj.getString("title"),
                    album = obj.getString("album"),
                    year = obj.getInt("year"),
                    runtime = obj.getInt("runtime"),
                    rating = obj.getInt("rating"),
                    votes = obj.getLong("votes"),
                    singers = singerList,
                    genres = genreList
                )
                songs.add(song)
            }
        }
    } finally {
        connection.disconnect()
    }
    return songs
}