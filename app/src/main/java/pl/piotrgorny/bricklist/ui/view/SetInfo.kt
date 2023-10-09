package pl.piotrgorny.bricklist.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pl.piotrgorny.bricklist.data.BrickSet

@Composable
fun SetInfo(brickSet: BrickSet) {
    Column {
        Text(text = brickSet.id)
        Text(text = brickSet.name)
        GlideImage(
//            modifier = Modifier.size(200.dp),
            imageModel = { brickSet.imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
        )
    }
}