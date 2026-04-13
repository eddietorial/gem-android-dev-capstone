package com.gem.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

// ---- Entity ---------------------------------------------------------------- 

@Entity(tableName = "menu_item_table")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

// ---- DAO -------------------------------------------------------------------

@Dao
interface MenuItemDao {

    /** Observe all items as LiveData so the UI auto-updates on DB changes. */
    @Query("SELECT * FROM menu_item_table ORDER BY title ASC")
    fun getAllMenuItems(): LiveData<List<MenuItemEntity>>

    /** Replace-on-conflict means re-fetching the menu is always safe. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuItemEntity>)
}

// ---- Database --------------------------------------------------------------

@Database(
    entities      = [MenuItemEntity::class],
    version       = 1,
    exportSchema  = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "little_lemon_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
