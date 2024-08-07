package com.llimapons.rickverse.presentation.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(CharactersState())
        private set


    fun onAction(action: CharactersActions){

    }
}