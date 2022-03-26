package com.example.gamergiveawaysapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Giveaways(
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("gamerpower_url")
    val gamerpowerUrl: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("open_giveaway")
    val openGiveaway: String,
    @SerializedName("open_giveaway_url")
    val openGiveawayUrl: String,
    @SerializedName("platforms")
    val platforms: String,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("users")
    val users: Int,
    @SerializedName("worth")
    val worth: String
): Parcelable