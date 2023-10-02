package pl.piotrgorny.bricklist.data

import RebrickableApi
import pl.piotrgorny.bricklist.data.api.BrickResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface SetRepository {
    suspend fun getSetParts(setId: String, pageId: Int? = null) : List<Part>
}

object SetRepositoryRetrofit : SetRepository {
    private val rebrickableApi: RebrickableApi = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://rebrickable.com/api/v3/lego/").build().create(RebrickableApi::class.java)
    override suspend fun getSetParts(setId: String, pageId: Int?): List<Part> {
        val parts = mutableListOf<Part>()
        val apiResult = rebrickableApi.getParts(setId, pageId ?: 1)
        parts.addAll(apiResult.results.map { it.toPart() })
        apiResult.next?.extractPageFromUrl()?.let {
            parts.addAll(getSetParts(setId, it))
        }
        return parts
    }

    private fun String.extractPageFromUrl(): Int? = this.split("page=")[1].toIntOrNull()
}

fun BrickResult.toPart() = Part(
    imageUrl = part.imageUrl,
    name = part.name,
    quantityNeeded = quantity
)

