package com.llimapons.rickverse.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _locationsState = MutableStateFlow<PagingData<LocationBO>>(PagingData.empty())
    val locationsState: StateFlow<PagingData<LocationBO>> = _locationsState

    init {
        viewModelScope.launch {
            locationRepository.getAllLocations()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _locationsState.value = pagingData
                }
        }
    }

    fun onAction(action: LocationsActions){

    }

}