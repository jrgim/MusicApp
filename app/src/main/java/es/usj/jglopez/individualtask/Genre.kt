package es.usj.jglopez.individualtask

data class Genre(
    val id: Long,
    val name: String,
    val songs: List<Song> = emptyList()
)