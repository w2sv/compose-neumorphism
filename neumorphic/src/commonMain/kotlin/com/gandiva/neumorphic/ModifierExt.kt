package com.gandiva.neumorphic

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gandiva.neumorphic.model.CornerShape
import com.gandiva.neumorphic.model.LightSource
import com.gandiva.neumorphic.model.NeuShape
import com.gandiva.neumorphic.model.NeuStyle

/**
 * Extension method to create neumorphic ui on compose-ui components.
 *
 * @param lightShadowColor Light shadow color.
 * @param darkShadowColor Dark shadow color.
 * @param shadowElevation Elevation or Depth.
 * @param shape Component shape.
 * @param lightSource Light-source direction used to draw light and dark shadow at different position.
 * @author Sridhar Subramani
 */
fun Modifier.neu(
    lightShadowColor: Color,
    darkShadowColor: Color,
    shadowElevation: Dp = 4.dp,
    shape: NeuShape = NeuShape.Flat(CornerShape.Rounded(0.dp)),
    lightSource: LightSource = LightSource.LEFT_TOP
) = this.then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            shape.draw(
                drawScope = this,
                style = NeuStyle(
                    lightShadowColor = lightShadowColor,
                    darkShadowColor = darkShadowColor,
                    shadowElevation = shadowElevation,
                    lightSource = lightSource
                )
            )
        }
    }
)
