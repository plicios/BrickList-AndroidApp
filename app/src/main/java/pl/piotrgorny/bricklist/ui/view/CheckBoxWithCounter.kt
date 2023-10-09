package pl.piotrgorny.bricklist.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CheckBoxWithCounter(
    currentStep: Int = 0,
    finalStep: Int = 1,
    onClick: () -> Unit = { }
){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$currentStep/$finalStep")
        Checkbox(checked = currentStep == finalStep, onCheckedChange = {
            onClick()
        })
    }
}