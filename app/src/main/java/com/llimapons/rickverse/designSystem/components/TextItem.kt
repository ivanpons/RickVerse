package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextItem(
    text: String,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable {
                onItemClicked()
            }
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}
