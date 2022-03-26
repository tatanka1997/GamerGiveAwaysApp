package com.example.gamergiveawaysapp.network

import com.example.gamergiveawaysapp.model.Giveaways
import com.example.gamergiveawaysapp.utils.PlatformType
import com.example.gamergiveawaysapp.utils.SortType
import retrofit2.Response

interface GiveawaysRepository {
    suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>>
    suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>>
}

class GiveawaysRepositoryImpl(
    private val giveawaysService: GiveawaysService
) : GiveawaysRepository {

    override suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>> =
        giveawaysService.getAllGiveaways(sortedBy.realValue)

    override suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>> =
        giveawaysService.getGiveawaysByPlatform(platform.realValue)

}