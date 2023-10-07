package pl.piotrgorny.bricklist.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import pl.piotrgorny.bricklist.data.Part

@Composable
fun PartsList(items: List<Part>, partUpdate: (part: Part) -> Unit) {
    LazyColumn {
        items(items) {
            PartRow(part = it) {part ->
                partUpdate(part)
            }
        }
    }
}