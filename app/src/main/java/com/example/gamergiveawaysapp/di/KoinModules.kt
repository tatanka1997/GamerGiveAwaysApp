package com.example.gamergiveawaysapp.di

import android.content.Context
import androidx.room.Room
import com.example.gamergiveawaysapp.database.DatabaseRepository
import com.example.gamergiveawaysapp.database.DatabaseRepositoryImpl
import com.example.gamergiveawaysapp.database.GiveawaysDAO
import com.example.gamergiveawaysapp.database.GiveawaysDatabase
import com.example.gamergiveawaysapp.network.GiveawaysRepository
import com.example.gamergiveawaysapp.network.GiveawaysRepositoryImpl
import com.example.gamergiveawaysapp.network.GiveawaysService
import com.example.gamergiveawaysapp.viewmodel.GiveawaysViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    fun providesNetworkService(okHttpClient: OkHttpClient): GiveawaysService =
        Retrofit.Builder()
            .baseUrl(GiveawaysService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawaysService::class.java)

    fun provideGiveawaysRepo(networkService: GiveawaysService): GiveawaysRepository =
        GiveawaysRepositoryImpl(networkService)

    single { providesLoggingInterceptor() }
    single { providesNetworkService(get()) }
    single { providesOkHttpClient(get()) }
    single { provideGiveawaysRepo(get()) }
}

val applicationModule = module {

    fun providesGiveawaysDatabase(context: Context): GiveawaysDatabase =
        Room.databaseBuilder(
            context,
            GiveawaysDatabase::class.java,
            "giveaways-db"
        ).build()

    fun providesGiveawaysDao(giveawaysDatabase: GiveawaysDatabase): GiveawaysDAO =
        giveawaysDatabase.getGiveawaysDao()

    fun providesDatabaseRepository(databaseDAO: GiveawaysDAO): DatabaseRepository =
        DatabaseRepositoryImpl(databaseDAO)

    single { providesGiveawaysDatabase(androidContext()) }
    single { providesDatabaseRepository(get()) }
    single { providesGiveawaysDao(get()) }
}

val viewModelModule = module {

    viewModel { GiveawaysViewModel(get(), get()) }
}