package com.example.gamergiveawaysapp.database

import com.example.gamergiveawaysapp.model.Giveaways

interface DatabaseRepository {
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)
    suspend fun getAllGiveaways(): List<Giveaways>
    suspend fun getGiveawaysById(searchId: Int): Giveaways
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>
    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
}

class DatabaseRepositoryImpl(
    private val giveawaysDatabase: GiveawaysDAO
) : DatabaseRepository {

    override suspend fun insertGiveaways(newGiveaways: List<Giveaways>) =
        giveawaysDatabase.insertGiveaways(newGiveaways)

    override suspend fun getAllGiveaways(): List<Giveaways> {
        return giveawaysDatabase.getAllGiveaways()
    }

    override suspend fun getGiveawaysById(searchId: Int): Giveaways {
        return giveawaysDatabase.getGiveawaysById(searchId)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> {
        return giveawaysDatabase.getGiveawaysByPlatform(platform)
    }

    override suspend fun deleteGiveaways(giveaways: List<Giveaways>) {
        return giveawaysDatabase.deleteGiveaways(giveaways)
    }

}