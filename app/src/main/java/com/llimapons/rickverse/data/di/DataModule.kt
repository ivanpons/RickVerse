package com.llimapons.rickverse.data.di

import com.llimapons.rickverse.data.networking.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().build()
    }

}