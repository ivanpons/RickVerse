package com.llimapons.rickverse.domain.useCase

import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.repositories.LocationRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import javax.inject.Inject

class GetFullLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(locationId: Int): Result<LocationBO, DataError.Network> {
        when(val locationResult = locationRepository.getLocation(locationId)){
            is Result.Error -> return locationResult
            is Result.Success -> {
                val charactersResult = characterRepository.getMultipleCharacters(locationResult.data.residentsId)
                when(charactersResult){
                    is Result.Error -> return Result.Success(locationResult.data)
                    is Result.Success -> {
                        val fullLocation = locationResult.data.copy(residents = charactersResult.data)
                        return Result.Success(fullLocation)
                    }
                }
            }
        }
    }

}