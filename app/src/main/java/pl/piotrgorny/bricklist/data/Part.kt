package pl.piotrgorny.bricklist.data

data class Part(
    val imageUrl: String,
    val name: String,
    val quantityNeeded: Int,
    val quantityFound: Int = 1
) {
    val allFound
        get() = quantityNeeded == quantityFound
}