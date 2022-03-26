package com.example.gamergiveawaysapp.utils

import com.example.gamergiveawaysapp.model.Giveaways

sealed class GiveawayState {
    object LOADING : GiveawayState()
    class SUCCESS<T>(val giveaways: T, isLocalData: Boolean = false) : GiveawayState()
    class ERROR(val error: Throwable) : GiveawayState()
}
