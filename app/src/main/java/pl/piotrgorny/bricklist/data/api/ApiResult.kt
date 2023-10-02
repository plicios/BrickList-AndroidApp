package pl.piotrgorny.bricklist.data.api

data class ApiResult<T>(
    val count: Int,
    val next: String?,
    val results: List<T>
)