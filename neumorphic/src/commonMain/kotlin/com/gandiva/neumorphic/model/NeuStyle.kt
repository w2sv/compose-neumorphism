package com.gandiva.neumorphic.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class NeuStyle(
    val lightShadowColor: Color,
    val darkShadowColor: Color,
    val shadowElevation: Dp,
    val lightSource: LightSource
)