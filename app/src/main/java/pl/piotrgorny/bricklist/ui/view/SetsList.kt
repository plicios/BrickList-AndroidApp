package pl.piotrgorny.bricklist.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.piotrgorny.bricklist.data.BrickSet

@Composable
fun SetsList(items: List<BrickSet>, onSetSelected: (BrickSet) -> Unit) {
    LazyColumn {
        items(items) {
            Box(Modifier.clickable { onSetSelected(it) }) {
                SetRow(set = it)
            }
        }
    }
}