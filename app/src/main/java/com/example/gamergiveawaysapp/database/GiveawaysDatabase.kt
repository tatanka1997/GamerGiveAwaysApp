package com.example.gamergiveawaysapp.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.gamergiveawaysapp.model.Giveaways

@Database(
    entities = [Giveaways::class],
    version = 1
)
abstract class GiveawaysDatabase : RoomDatabase() {
    abstract fun getGiveawaysDao(): GiveawaysDAO
}

@Dao
interface GiveawaysDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)

    @Query("SELECT * FROM giveaways")
    suspend fun getAllGiveaways(): List<Giveaways>

    @Query("SELECT * FROM giveaways WHERE id = :searchId LIMIT 1")
    suspend fun getGiveawaysById(searchId: Int): Giveaways

    @Query("SELECT * FROM giveaways WHERE platforms = :platform")
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>

    @Delete
    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
}