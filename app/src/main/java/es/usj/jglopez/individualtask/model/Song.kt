package es.usj.jglopez.individualtask.model

data class Song(
    val id: Long,
    val title: String,
    val album: String,
    val year: Int,
    val runtime: Int,
    val rating: Int,
    val votes: Long,
    val singers: List<Singer>,
    val genres: List<Genre>,
    var isFavorite: Boolean = false
)
