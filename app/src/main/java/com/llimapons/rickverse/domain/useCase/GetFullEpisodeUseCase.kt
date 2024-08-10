package com.llimapons.rickverse.domain.useCase

import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.repositories.EpisodeRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import javax.inject.Inject

class GetFullEpisodeUseCase @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(episodeId: Int): Result<EpisodeBO, DataError.Network> {
        when (val episodeResult = episodeRepository.getEpisode(episodeId)) {
            is Result.Error -> return episodeResult
            is Result.Success -> {
                val charactersResult =
                    characterRepository.getMultipleCharacters(episodeResult.data.charactersId)
                when (charactersResult) {
                    is Result.Error -> return Result.Success(episodeResult.data)
                    is Result.Success -> {
                        val fullEpisode =
                            episodeResult.data.copy(characters = charactersResult.data)
                        return Result.Success(fullEpisode)
                    }
                }
            }
        }
    }

}