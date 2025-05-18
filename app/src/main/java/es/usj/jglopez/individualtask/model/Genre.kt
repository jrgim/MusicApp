package es.usj.jglopez.individualtask.model

data class Genre(
    val id: Long,
    val name: String,
    val songs: List<Song> = emptyList()
)