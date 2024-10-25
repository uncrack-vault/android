package com.aritradas.uncrack.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.round

@Composable
private fun ShieldProgressBar(passwordScores: Float, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .width(250.dp)
            .height(250.dp)
            .padding(16.dp)
    ) {
        drawArc(
            brush = SolidColor(Color.LightGray),
            startAngle = 120f,
            sweepAngle = 300f,
            useCenter = false,
            style = Stroke(45f, cap = StrokeCap.Round)
        )

        val passwordHealth = passwordScores * 30

        drawArc(
            brush = SolidColor(Color.Green),
            startAngle = 120f,
            sweepAngle = passwordHealth,
            useCenter = false,
            style = Stroke(35f, cap = StrokeCap.Round)
        )

        drawIntoCanvas {
            val paint = Paint().asFrameworkPaint()
            paint.apply {
                isAntiAlias = true
                textSize = 55f
                textAlign = android.graphics.Paint.Align.CENTER
            }
            it.nativeCanvas.drawText(
                "${round(passwordScores * 10).toInt()}",
                size.width/2,
                size.height/2,
                paint
            )
        }
    }
}

@Preview
@Composable
private fun BarPreview() {
    ShieldProgressBar(passwordScores = 10f)
}