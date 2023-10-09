package pl.piotrgorny.bricklist.data


sealed class Result {
    object Loading : Result()
    object Success : Result()
    data class Error(val errorMessage: String = "") : Result()
}
