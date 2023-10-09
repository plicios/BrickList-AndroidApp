package pl.piotrgorny.bricklist.data

data class Part(
    val id: String,
    val imageUrl: String,
    val name: String,
    val color: String,
    val quantityNeeded: Int,
    val quantityFound: Int = 0
) {
    val allFound
        get() = quantityNeeded == quantityFound
}