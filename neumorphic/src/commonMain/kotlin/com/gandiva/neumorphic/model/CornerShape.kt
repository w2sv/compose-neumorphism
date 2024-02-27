package com.gandiva.neumorphic.model

import androidx.compose.ui.unit.Dp

sealed interface CornerShape {
    data object Oval : CornerShape
    data class Rounded(val radius: Dp) : CornerShape
}
