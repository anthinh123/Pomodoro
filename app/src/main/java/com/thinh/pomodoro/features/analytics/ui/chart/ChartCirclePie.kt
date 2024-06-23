package com.thinh.pomodoro.features.analytics.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChartCirclePie(
    modifier: Modifier,
    charts: List<ChartModel>,
    totalTime: Int,
    size: Dp = 200.dp,
    strokeWidth: Dp = 16.dp,
    text: String,
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text))
    val textSize = textLayoutResult.size

    Canvas(modifier = modifier
        .size(size)
        .padding(12.dp),
        onDraw = {
            var startAngle = 0f
            var sweepAngle = 0f

            charts.forEach {

                sweepAngle = (it.value / totalTime) * 360

                drawArc(
                    color = it.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                startAngle += sweepAngle
            }

//            drawText(
//                textMeasurer = textMeasurer,
//                text = text,
//                topLeft = Offset(
//                    (this.size.width - textSize.width + strokeWidth.toPx()) / 2f,
//                    (this.size.height - textSize.height) / 2f
//                ),
//            )
        })

}

@Composable
@Preview
private fun ChartCirclePiePreview() {
    ChartCirclePie(
        modifier = Modifier,
        charts = listOf(
            ChartModel(50f, Color.Red),
            ChartModel(30f, Color.Green),
            ChartModel(20f, Color.Blue),
        ),
        totalTime = 100,
        text = "100% better than yesterday",
    )
}