package pl.piotrgorny.bricklist.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.piotrgorny.bricklist.data.database.SetEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class SetEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    val name: String,
    val imageUrl: String
) {
    companion object {
        const val TABLE_NAME = "sets"
        const val COLUMN_ID = "id"
    }
}
