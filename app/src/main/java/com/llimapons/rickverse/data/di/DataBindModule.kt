package com.llimapons.rickverse.data.di

import com.llimapons.rickverse.data.KtorCharacterRepository
import com.llimapons.rickverse.data.KtorEpisodeRepository
import com.llimapons.rickverse.data.KtorLocationRepository
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.repositories.EpisodeRepository
import com.llimapons.rickverse.domain.repositories.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    abstract fun bindCharacterRepository(characterRepository: KtorCharacterRepository): CharacterRepository

    @Binds
    abstract fun bindLocationRepository(locationRepository: KtorLocationRepository): LocationRepository

    @Binds
    abstract fun bindEpisodeRepository(episodeRepository: KtorEpisodeRepository): EpisodeRepository
}