package es.usj.jglopez.individualtask

data class Singer(
    val id: Long,
    val name: String,
    val songs: List<Song> = emptyList()
)