package com.gandiva.neumorphic

import androidx.annotation.ColorInt
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import com.gandiva.neumorphic.model.CornerShape
import com.gandiva.neumorphic.model.LightSource
import com.gandiva.neumorphic.model.NeuShape
import com.gandiva.neumorphic.model.NeuStyle

internal expect fun Any.makeNormalBlur(
    blurRadius: Float,
    @ColorInt color: Int,
    strokeWidth: Float? = null
)

internal fun ContentDrawScope.drawBackgroundShadows(
    neuShape: NeuShape,
    style: NeuStyle
) {
    val elevation = style.shadowElevation.toPx()

    if (elevation > 0) {
        drawBlurredBackground(
            lightSource = style.lightSource,
            elevation = elevation,
            color = style.lightShadowColor.toArgb(),
            cornerShape = neuShape.cornerShape
        )
        drawBlurredBackground(
            lightSource = style.lightSource.opposite(),
            elevation = elevation,
            color = style.darkShadowColor.toArgb(),
            cornerShape = neuShape.cornerShape
        )
    }
}

private fun ContentDrawScope.drawBlurredBackground(
    lightSource: LightSource,
    elevation: Float,
    @ColorInt color: Int,
    cornerShape: CornerShape
) {
    drawIntoCanvas { canvas ->
        val blurRadius = elevation * .95f
        val displacement = elevation * .6f

        val paint = Paint().also { p ->
            p.asFrameworkPaint().also { nativePaint ->
                nativePaint.isAntiAlias = true
                nativePaint.isDither = true
                nativePaint.color = color
                nativePaint.makeNormalBlur(blurRadius, color)
            }
        }

        val backgroundOffset = when (lightSource) {
            LightSource.LEFT_TOP -> Offset(-displacement, -displacement)
            LightSource.RIGHT_TOP -> Offset(displacement, -displacement)
            LightSource.LEFT_BOTTOM -> Offset(-displacement, displacement)
            LightSource.RIGHT_BOTTOM -> Offset(displacement, displacement)
        }

        canvas.save()
        canvas.translate(backgroundOffset.x, backgroundOffset.y)
        when (cornerShape) {
            is CornerShape.Oval -> canvas.drawOval(0f, 0f, this.size.width, this.size.height, paint)
            is CornerShape.Rounded -> canvas.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                cornerShape.radius.toPx(),
                cornerShape.radius.toPx(),
                paint
            )
        }
        canvas.restore()
    }
}

internal fun ContentDrawScope.drawForegroundShadows(
    neuShape: NeuShape,
    style: NeuStyle
) {
    val elevation = style.shadowElevation.toPx()

    if (elevation > 0) {
        drawForeground(
            lightSource = style.lightSource,
            elevation = elevation,
            color = style.darkShadowColor.toArgb(),
            cornerShape = neuShape.cornerShape
        )
        drawForeground(
            lightSource = style.lightSource.opposite(),
            elevation = elevation,
            color = style.lightShadowColor.toArgb(),
            cornerShape = neuShape.cornerShape
        )
    }
}

private fun ContentDrawScope.drawForeground(
    lightSource: LightSource,
    elevation: Float,
    @ColorInt color: Int,
    cornerShape: CornerShape
) {

    drawIntoCanvas { canvas ->
        val blurRadius = elevation * 0.6f
        val strokeWidth = elevation * .95f

        val paint = Paint().also { p ->
            p.asFrameworkPaint().also { nativePaint ->
                nativePaint.isAntiAlias = true
                nativePaint.color = color
                nativePaint.makeNormalBlur(blurRadius, color, strokeWidth)
            }
        }

        val backgroundOffset = when (lightSource) {
            LightSource.LEFT_TOP -> Offset(strokeWidth, strokeWidth)
            LightSource.RIGHT_TOP -> Offset(-strokeWidth, strokeWidth)
            LightSource.LEFT_BOTTOM -> Offset(strokeWidth, -strokeWidth)
            LightSource.RIGHT_BOTTOM -> Offset(-strokeWidth, -strokeWidth)
        }

        canvas.save()
        when (cornerShape) {
            is CornerShape.Oval -> {
                val visiblePath = Path().also { p ->
                    p.moveTo(0f, 0f)
                    p.addOval(Rect(0f, 0f, this.size.width, this.size.height))
                }
                canvas.clipPath(visiblePath)
                canvas.translate(backgroundOffset.x, backgroundOffset.y)
                canvas.drawOval(
                    -strokeWidth,
                    -strokeWidth,
                    this.size.width + strokeWidth,
                    this.size.height + strokeWidth,
                    paint
                )
            }

            is CornerShape.Rounded -> {
                val cornerRadius = cornerShape.radius.toPx()
                val visiblePath = Path().also { p ->
                    p.moveTo(0f, 0f)
                    p.addRoundRect(
                        RoundRect(
                            0f,
                            0f,
                            this.size.width,
                            this.size.height,
                            cornerRadius,
                            cornerRadius
                        )
                    )
                }

                canvas.clipPath(visiblePath)

                canvas.translate(backgroundOffset.x, backgroundOffset.y)
                canvas.drawRoundRect(
                    -strokeWidth,
                    -strokeWidth,
                    this.size.width + strokeWidth,
                    this.size.height + strokeWidth,
                    cornerRadius,
                    cornerRadius,
                    paint
                )
            }
        }
        canvas.restore()
    }
}
