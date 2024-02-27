package com.gandiva.neumorphism

import App
import CheckBoxAndRadioButtons
import CircleActionButton
import FlatButton
import FlatSlider
import InputBoxWithCardWrapper
import PlainInputBox
import PressedButton
import PressedSlider
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
private fun AppAndroidPreview() {
    App()
}

@Preview
@Composable
private fun InputBoxWithCardWrapperPreview() {
    InputBoxWithCardWrapper()
}

@Preview
@Composable
private fun PlainInputBoxPreview() {
    PlainInputBox()
}

@Preview
@Composable
private fun CheckBoxAndRadioButtonsPreview() {
    CheckBoxAndRadioButtons()
}

@Preview
@Composable
private fun PressedSliderPreview() {
    PressedSlider()
}

@Preview
@Composable
private fun FlatSliderPreview() {
    FlatSlider()
}

@Preview
@Composable
private fun PressedButtonPreview() {
    PressedButton()
}

@Preview
@Composable
private fun FlatButtonPreview() {
    FlatButton()
}

@Preview
@Composable
private fun CircleActionButtonPreview() {
    CircleActionButton()
}
