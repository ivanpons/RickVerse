package com.llimapons.rickverse.data.di

import com.llimapons.rickverse.data.KtorCharacterRepository
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    abstract fun bindCharacterRepository(characterRepository: KtorCharacterRepository): CharacterRepository
}