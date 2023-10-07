package pl.piotrgorny.bricklist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SetBrickDao {
    @Insert
    suspend fun insertSet(set: SetEntity)

    @Insert
    suspend fun insertBricks(vararg bricks: BrickEntity)

    @Update
    suspend fun updateBricks(vararg bricks: BrickEntity)

    @Query("SELECT * FROM ${SetEntity.TABLE_NAME}")
    fun getSets() : Flow<List<SetEntity>>

    @Query("SELECT * FROM ${SetEntity.TABLE_NAME} WHERE ${SetEntity.COLUMN_ID} = :id")
    fun getSet(id: String) : Flow<SetWithBricks>

    @Query("SELECT * FROM ${BrickEntity.TABLE_NAME}")
    fun getMissingBricks() : Flow<List<BrickEntity>>
}