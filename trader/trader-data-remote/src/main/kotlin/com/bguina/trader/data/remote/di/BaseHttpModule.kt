package com.bguina.trader.data.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
object BaseHttpModule {
    @[Provides Singleton]
    fun provideBaseGson(
    ) : Gson = GsonBuilder().create()

    @[Provides Singleton]
    fun provideBaseHttpClient(
    ) : OkHttpClient = OkHttpClient.Builder()
        .build()
}
