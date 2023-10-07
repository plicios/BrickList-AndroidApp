package pl.piotrgorny.bricklist.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.piotrgorny.bricklist.data.database.BrickEntity.Companion.COLUMN_COLOR
import pl.piotrgorny.bricklist.data.database.BrickEntity.Companion.COLUMN_ID
import pl.piotrgorny.bricklist.data.database.BrickEntity.Companion.COLUMN_SET_ID
import pl.piotrgorny.bricklist.data.database.BrickEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = [COLUMN_ID, COLUMN_SET_ID, COLUMN_COLOR])
data class BrickEntity(
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_SET_ID)
    val setId: String,
    val name: String,
    @ColumnInfo(name = COLUMN_COLOR)
    val color: String,
    val imageUrl: String,
    val quantityNeeded: Int,
    val quantityFound: Int
) {
    companion object {
        const val TABLE_NAME = "bricks"
        const val COLUMN_ID = "id"
        const val COLUMN_SET_ID = "set_id"
        const val COLUMN_COLOR = "color"
    }
}
