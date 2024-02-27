package com.gandiva.neumorphic.model

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.gandiva.neumorphic.drawBackgroundShadows
import com.gandiva.neumorphic.drawForegroundShadows

sealed interface NeuShape {
    val cornerShape: CornerShape
    fun draw(drawScope: ContentDrawScope, style: NeuStyle)

    data class Flat(override val cornerShape: CornerShape) : NeuShape {
        override fun draw(drawScope: ContentDrawScope, style: NeuStyle) {
            drawScope.drawBackgroundShadows(this, style)
            drawScope.drawContent()
        }
    }

    data class Pressed(override val cornerShape: CornerShape) : NeuShape {
        override fun draw(drawScope: ContentDrawScope, style: NeuStyle) {
            drawScope.drawContent()
            drawScope.drawForegroundShadows(this, style)
        }
    }
}