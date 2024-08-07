package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.llimapons.rickverse.domain.model.CharacterBO

@Composable
fun CharacterGrid(
    characters: List<CharacterBO>,
    onCharacterClicked: (CharacterBO) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 16.dp),
        columns = GridCells.FixedSize(size = 160.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
       items(characters){ character ->
           CharacterItem(
               character = character,
               onItemClick = {
                   onCharacterClicked(character)
               },
               size = DpSize(120.dp, 120.dp)
           )
       }
    }
}