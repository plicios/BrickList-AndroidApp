package pl.piotrgorny.bricklist.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class SetWithBricks(
    @Embedded
    val set: SetEntity,
    @Relation(
        parentColumn = SetEntity.COLUMN_ID,
        entityColumn = BrickEntity.COLUMN_SET_ID
    )
    val bricks: List<BrickEntity>
)
