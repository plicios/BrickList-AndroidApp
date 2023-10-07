package pl.piotrgorny.bricklist.data

import RebrickableApi
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import pl.piotrgorny.bricklist.data.api.BrickResult
import pl.piotrgorny.bricklist.data.database.BrickEntity
import pl.piotrgorny.bricklist.data.database.SetBrickDatabase
import pl.piotrgorny.bricklist.data.database.SetEntity
import pl.piotrgorny.bricklist.data.database.SetWithBricks
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface SetRepository {
    fun getSets() : Flow<List<BrickSet>>
    fun getSetWithParts(setId: String) : Flow<BrickSet>
    fun addSet(setId: String) : Flow<Result>
    fun getAllMissingParts() : Flow<List<Part>>
    suspend fun updatePart(part: Part, setId: String)
    suspend fun clearFoundParts(set: BrickSet)
}

class SetRepositoryRetrofitWithDatabase(applicationContext: Context) : SetRepository {
    private val rebrickableApi: RebrickableApi = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://rebrickable.com/api/v3/lego/").build().create(RebrickableApi::class.java)
    private val database: SetBrickDatabase = Room.databaseBuilder(
        applicationContext,
        SetBrickDatabase::class.java, "brickSetDatabase"
    ).build()
    private val brickSetDao by lazy { database.setBrickDao() }

    override fun getSets(): Flow<List<BrickSet>> {
        return brickSetDao.getSets().map { it.toBrickSets() }
    }

    override fun getSetWithParts(setId: String): Flow<BrickSet> {
        return brickSetDao.getSet(setId).map { it.toBrickSet() }
    }

    override fun addSet(setId: String): Flow<Result> = flow {
        emit(Result.Loading)
        val set = getSet(setId)
        database.withTransaction {
            brickSetDao.insertSet(SetEntity(set.id, set.name, set.imageUrl))
            set.parts.toBrickEntities(setId).sortedBy { it.id }.forEachIndexed { i, elem ->
                Log.d("XXX", "$i: $elem")
                brickSetDao.insertBricks(elem)
            }
//            brickSetDao.insertBricks(*set.parts.toBrickEntities(setId).toTypedArray())
        }
        emit(Result.Success)
    }

    private fun Part.toBrickEntity(setId: String) = BrickEntity(id = id, setId = setId, name = name, imageUrl = imageUrl, color = color, quantityNeeded = quantityNeeded, quantityFound = quantityFound)
    private fun BrickEntity.toPart() = Part(id = id, name = name, imageUrl = imageUrl, color = color, quantityNeeded = quantityNeeded, quantityFound = quantityFound)
    private fun SetWithBricks.toBrickSet() = BrickSet(id = set.id, name = set.name, imageUrl = set.imageUrl, parts = bricks.toParts())
    private fun SetEntity.toBrickSet() = BrickSet(id = id, name = name, imageUrl = imageUrl, parts = emptyList())
    private fun List<SetEntity>.toBrickSets() = this.map { it.toBrickSet() }
    private fun List<BrickEntity>.toParts() = this.map { it.toPart() }
    private fun List<Part>.toBrickEntities(setId: String) = this.map { it.toBrickEntity(setId) }

    override fun getAllMissingParts(): Flow<List<Part>> = brickSetDao.getMissingBricks().map { it.toParts() }

    override suspend fun updatePart(part: Part, setId: String) {
        brickSetDao.updateBricks(part.toBrickEntity(setId))
    }

    override suspend fun clearFoundParts(set: BrickSet) {
        brickSetDao.updateBricks(*set.parts.map { it.toBrickEntity(set.id).copy(quantityFound = 0) }.toTypedArray())
    }

    private suspend fun getSet(setId: String): BrickSet {
        val setResult = rebrickableApi.getSet(setId)
        return BrickSet(
            setId,
            setResult.name,
            setResult.imageUrl,
            getSetParts(setId)
        )
    }

    private suspend fun getSetParts(setId: String, pageId: Int? = null): List<Part> {
        val parts = mutableListOf<Part>()
        val apiResult = rebrickableApi.getParts(setId, pageId ?: 1)
        parts.addAll(apiResult.results.filter { !it.isSpare }.map { it.toPart() })
        apiResult.next?.extractPageFromUrl()?.let {
            parts.addAll(getSetParts(setId, it))
        }
        return parts
    }

    private fun String.extractPageFromUrl(): Int? = this.split("page=")[1].toIntOrNull()
}

fun BrickResult.toPart() = Part(
    id = part.partNumber,
    imageUrl = part.imageUrl,
    name = part.name,
    color = color.name,
    quantityNeeded = quantity
)

