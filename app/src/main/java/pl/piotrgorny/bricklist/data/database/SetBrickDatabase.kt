package pl.piotrgorny.bricklist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SetEntity::class, BrickEntity::class], version = 1, exportSchema = false)
abstract class SetBrickDatabase : RoomDatabase() {
    abstract fun setBrickDao() : SetBrickDao
}