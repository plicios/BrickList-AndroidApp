package pl.piotrgorny.bricklist.data

data class BrickSet(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parts: List<Part>
)
