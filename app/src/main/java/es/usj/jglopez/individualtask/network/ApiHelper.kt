package es.usj.jglopez.individualtask.network

import android.util.Log
import es.usj.jglopez.individualtask.model.Genre
import es.usj.jglopez.individualtask.model.GenreCache
import es.usj.jglopez.individualtask.model.Singer
import es.usj.jglopez.individualtask.model.SingerCache
import es.usj.jglopez.individualtask.model.Song
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
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

                val singersJson = obj.getJSONArray("singers")
                val singerList = mutableListOf<Singer>()
                for (j in 0 until singersJson.length()) {
                    val singerId = singersJson.getLong(j)
                    SingerCache.singers.find { it.id == singerId }?.let { singerList.add(it) }
                }

                val genresJson = obj.getJSONArray("genres")
                val genreList = mutableListOf<Genre>()
                for (j in 0 until genresJson.length()) {
                    val genreId = genresJson.getLong(j)
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

// Envía una nueva canción a la API (POST /songs). Devuelve true si la operación fue exitosa.
fun postSong(song: Song): Boolean {
    val url = URL("http://10.0.2.2:8080/songs")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "application/json")
    connection.doOutput = true
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    val json = JSONObject().apply {
        put("title", song.title)
        put("album", song.album)
        put("year", song.year)
        put("runtime", song.runtime)
        put("rating", song.rating)
        put("votes", song.votes)
        put("singers", JSONArray(song.singers.map { it.id }))
        put("genres", JSONArray(song.genres.map { it.id }))
    }

    try {
        OutputStreamWriter(connection.outputStream).use { it.write(json.toString()) }
        val responseCode = connection.responseCode
        // Lee la respuesta para completar la petición
        if (responseCode in 200..299) {
            connection.inputStream?.close()
            return true
        } else {
            connection.errorStream?.close()
            return false
        }
    } finally {
        connection.disconnect()
    }
}

fun putSong(song: Song): Boolean {
    val url = URL("http://10.0.2.2:8080/songs")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "PUT"
    connection.setRequestProperty("Content-Type", "application/json")
    connection.setRequestProperty("Accept", "application/json")
    connection.doOutput = true
    connection.doInput = true
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    val json = JSONObject().apply {
        put("id", song.id) // La API requiere el id en el cuerpo
        put("title", song.title)
        put("album", song.album)
        put("year", song.year)
        put("runtime", song.runtime)
        put("rating", song.rating)
        put("votes", song.votes)
        put("singers", JSONArray(song.singers.map { it.id }))
        put("genres", JSONArray(song.genres.map { it.id }))
    }

    try {
        OutputStreamWriter(connection.outputStream).use { it.write(json.toString()) }
        connection.outputStream.flush()
        // Leer la respuesta para completar la petición
        val responseCode = connection.responseCode
        if (responseCode in 200..299) {
            connection.inputStream?.bufferedReader()?.use { it.readText() }
            return true
        } else {
            // Opcional: imprime el error para depuración
            val errorMsg = connection.errorStream?.bufferedReader()?.use { it.readText() }
            println("PUT error: $responseCode, $errorMsg")
            connection.errorStream?.close()
            return false
        }
    } finally {
        connection.disconnect()
    }
}