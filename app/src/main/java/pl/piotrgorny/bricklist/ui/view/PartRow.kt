package pl.piotrgorny.bricklist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Update
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pl.piotrgorny.bricklist.data.Part
import pl.piotrgorny.bricklist.ui.theme.BrickListTheme

@Composable
fun PartRow(part: Part, modifier: Modifier = Modifier, partUpdate: (part: Part) -> Unit) {
    Row(modifier.background(
        if(part.allFound) Color(122, 213, 167, 255) else Color.LightGray).padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier.size(80.dp),
            imageModel = { part.imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
        )
        Column(Modifier.width(0.dp).weight(1f).padding(start = 5.dp, end = 5.dp)) {
            Text(
                text = part.name,
            )
        }
        CheckBoxWithCounter(part.quantityFound, part.quantityNeeded) {
            if(part.quantityFound < part.quantityNeeded){
                partUpdate(part.copy(quantityFound = part.quantityFound + 1))
            } else {
                partUpdate(part.copy(quantityFound = 0))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PartRowPreview() {
    BrickListTheme {
        PartRow(
            Part(
                "https://cdn.rebrickable.com/media/parts/ldraw/14/2342.png",
                "test",
                "test part",
                "black",
                1,
                0
            )
        ) {

        }
    }
}