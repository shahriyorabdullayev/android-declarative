package com.shahriyor.android_declarative.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.shahriyor.android_declarative.data.db.AppDatabase
import com.shahriyor.android_declarative.data.db.TVShowDao
import com.shahriyor.android_declarative.data.network.Server.IS_TESTER
import com.shahriyor.android_declarative.data.network.Server.SERVER_DEPLOYMENT
import com.shahriyor.android_declarative.data.network.Server.SERVER_DEVELOPMENT
import com.shahriyor.android_declarative.data.network.TVSHowService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_DEPLOYMENT
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVSHowService {
        return retrofitClient().create(TVSHowService::class.java)
    }

    @Provides
    @Singleton
    fun appDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase): TVShowDao {
        return appDatabase.getTVShowDao()
    }
}