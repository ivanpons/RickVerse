package com.llimapons.rickverse.presentation.locationInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llimapons.rickverse.domain.useCase.GetFullLocationUseCase
import com.llimapons.rickverse.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationInfoViewModel @Inject constructor(
    private val getFullLocationUseCase: GetFullLocationUseCase
) : ViewModel() {

    var state by mutableStateOf(LocationInfoState())
        private set

    init {
        state = state.copy(isLoading = true)
    }

    fun onAction(action: LocationInfoActions) {
        when (action) {
            LocationInfoActions.OnBackClicked -> Unit
            is LocationInfoActions.LoadLocation -> {
             getFullLocatio(action.locationId)
            }

            is LocationInfoActions.CharacterClicked -> Unit
        }
    }

    private fun getFullLocatio(locationId: Int) {
        viewModelScope.launch {
            when(val result = getFullLocationUseCase(locationId)){
                is Result.Error -> {
                    state = state.copy(isLoading = false)
                }
                is Result.Success -> {
                    state = state.copy(
                        isLoading = false,
                        location = result.data
                    )
                }
            }
        }
    }

}