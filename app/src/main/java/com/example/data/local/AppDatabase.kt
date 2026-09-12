package com.example.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "saved_events")
data class SavedEventEntity(
    @PrimaryKey val eventId: String,
    val title: String,
    val organizationName: String,
    val category: String,
    val dateText: String,
    val locationName: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Dao
interface SavedEventDao {
    @Query("SELECT * FROM saved_events ORDER BY savedAt DESC")
    fun getAllSavedEvents(): Flow<List<SavedEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvent(event: SavedEventEntity)

    @Query("DELETE FROM saved_events WHERE eventId = :eventId")
    suspend fun removeEvent(eventId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_events WHERE eventId = :eventId)")
    fun isEventSaved(eventId: String): Flow<Boolean>
}

@Database(entities = [SavedEventEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedEventDao(): SavedEventDao
}
