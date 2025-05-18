package es.usj.jglopez.individualtask.model

data class Singer(
    val id: Long,
    val name: String,
    val songs: List<Song> = emptyList()
)