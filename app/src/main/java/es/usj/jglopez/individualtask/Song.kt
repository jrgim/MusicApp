package es.usj.jglopez.individualtask

data class Song(
    val id: Long,
    val title: String,
    val album: String,
    val year: Int,
    val runtime: Int,
    val rating: Int,
    val votes: Long,
    val singers: List<Int>,
    val genres: List<Int>
)
