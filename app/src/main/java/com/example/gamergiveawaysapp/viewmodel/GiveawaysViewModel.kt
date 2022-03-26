package com.example.gamergiveawaysapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamergiveawaysapp.database.DatabaseRepository
import com.example.gamergiveawaysapp.model.Giveaways
import com.example.gamergiveawaysapp.network.GiveawaysRepository
import com.example.gamergiveawaysapp.utils.GiveawayState
import com.example.gamergiveawaysapp.utils.PlatformType
import com.example.gamergiveawaysapp.utils.SortType
import kotlinx.coroutines.*

class GiveawaysViewModel(
    private val networkRepo: GiveawaysRepository,
    private val databaseRepo: DatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    init {
        Log.d("GiveawaysViewModel", "VIEWMODEL initialized")
    }

    var platform: PlatformType = PlatformType.ANDROID

    private val _sortedGiveaways: MutableLiveData<GiveawayState> = MutableLiveData(GiveawayState.LOADING)
    val giveaways: LiveData<GiveawayState> get() = _sortedGiveaways

    fun getSortedGiveaways(sortBy: SortType = SortType.DATE) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getAllGiveaways(sortBy)
                if(response.isSuccessful) {
                    response.body()?.let {
                        databaseRepo.insertGiveaways(it)
                        val localData = databaseRepo.getAllGiveaways()
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData))
                    } ?: throw Exception("Response in null")
                } else {
                    throw Exception("No successful response")
                }
            } catch (e: Exception) {
                // we catch all errors
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                loadFromDB()
            }
        }
    }

    private suspend fun loadFromDB() {
        try {
            val localData = databaseRepo.getAllGiveaways()
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData, isLocalData = true))
        } catch (e: Exception) {
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }

    fun getGiveawaysByPlatform() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getGiveawaysByPlatform(platform)
                if(response.isSuccessful) {
                    response.body()?.let {
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(it))
                    } ?: throw Exception("Response in null")
                } else {
                    throw Exception("No successful response")
                }
            } catch (e: Exception) {
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
            }
        }

        viewModelScope.async {

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GiveawaysViewModel", "VIEWMODEL destroyed")
    }
}