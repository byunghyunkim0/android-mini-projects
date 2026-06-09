package com.woowa.nureongi.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.model.MiniMapUiModel
import com.woowa.nureongi.ui.model.RouteNodeUiModel
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

private const val GRID_LINE_ALPHA = 0.3f
private const val GRID_LINE_STROKE_WIDTH = 2f
private val GRID_LINE_DASH_PATTERN = floatArrayOf(10f, 10f)
private const val GRID_DOT_ALPHA = 0.5f
private const val GRID_DOT_RADIUS = 5f

private const val NODE_RADIUS = 8f
private const val HIGHLIGHTED_NODE_RADIUS = 14f
private const val HIGHLIGHTED_NODE_HOLE_RADIUS_RATIO = 0.5f
private const val ROUTE_LINE_STROKE_WIDTH = 6f
private const val LABEL_OFFSET = 6f

@Composable
fun TactileMiniMap(
    uiModel: MiniMapUiModel,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = NureongiTypography.ItemDescription.fontSize,
        color = NureongiColors.Accent,
    )
    val routeDescription = uiModel.path.joinToString(separator = " → ") { it.label ?: "경유 지점" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(NureongiColors.Surface)
            .padding(20.dp),
    ) {
        Text(
            text = uiModel.title,
            style = NureongiTypography.ItemDescription,
            color = NureongiColors.TextSecondary,
        )
        Box(modifier = Modifier.padding(top = 12.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .semantics { contentDescription = "${uiModel.title} 경로: $routeDescription" },
            ) {
                if (uiModel.rows < 2 || uiModel.columns < 2) return@Canvas

                val cellWidth = size.width / (uiModel.columns - 1)
                val cellHeight = size.height / (uiModel.rows - 1)

                drawBackgroundGrid(rows = uiModel.rows, columns = uiModel.columns, cellWidth = cellWidth, cellHeight = cellHeight)
                drawHighlightedRoute(
                    path = uiModel.path,
                    cellWidth = cellWidth,
                    cellHeight = cellHeight,
                    textMeasurer = textMeasurer,
                    labelStyle = labelStyle,
                )
            }
        }
    }
}

private fun cellOffset(row: Int, column: Int, cellWidth: Float, cellHeight: Float): Offset =
    Offset(x = column * cellWidth, y = row * cellHeight)

private fun DrawScope.drawBackgroundGrid(rows: Int, columns: Int, cellWidth: Float, cellHeight: Float) {
    val lineColor = NureongiColors.TextSecondary.copy(alpha = GRID_LINE_ALPHA)
    val dashedStroke = Stroke(
        width = GRID_LINE_STROKE_WIDTH,
        pathEffect = PathEffect.dashPathEffect(GRID_LINE_DASH_PATTERN),
    )

    for (row in 0 until rows) {
        drawLine(
            color = lineColor,
            start = cellOffset(row, 0, cellWidth, cellHeight),
            end = cellOffset(row, columns - 1, cellWidth, cellHeight),
            strokeWidth = dashedStroke.width,
            pathEffect = dashedStroke.pathEffect,
        )
    }
    for (column in 0 until columns) {
        drawLine(
            color = lineColor,
            start = cellOffset(0, column, cellWidth, cellHeight),
            end = cellOffset(rows - 1, column, cellWidth, cellHeight),
            strokeWidth = dashedStroke.width,
            pathEffect = dashedStroke.pathEffect,
        )
    }
    for (row in 0 until rows) {
        for (column in 0 until columns) {
            drawCircle(
                color = NureongiColors.TextSecondary.copy(alpha = GRID_DOT_ALPHA),
                radius = GRID_DOT_RADIUS,
                center = cellOffset(row, column, cellWidth, cellHeight),
            )
        }
    }
}

private fun DrawScope.drawHighlightedRoute(
    path: List<RouteNodeUiModel>,
    cellWidth: Float,
    cellHeight: Float,
    textMeasurer: TextMeasurer,
    labelStyle: TextStyle,
) {
    for (index in 0 until path.size - 1) {
        val from = path[index]
        val to = path[index + 1]
        drawLine(
            color = NureongiColors.Accent,
            start = cellOffset(from.row, from.column, cellWidth, cellHeight),
            end = cellOffset(to.row, to.column, cellWidth, cellHeight),
            strokeWidth = ROUTE_LINE_STROKE_WIDTH,
        )
    }
    path.forEach { node ->
        val center = cellOffset(node.row, node.column, cellWidth, cellHeight)
        val radius = if (node.state == RouteNodeUiModel.State.HIGHLIGHTED) {
            HIGHLIGHTED_NODE_RADIUS
        } else {
            NODE_RADIUS
        }
        drawCircle(color = NureongiColors.Accent, radius = radius, center = center)
        if (node.state == RouteNodeUiModel.State.HIGHLIGHTED) {
            drawCircle(
                color = NureongiColors.Background,
                radius = radius * HIGHLIGHTED_NODE_HOLE_RADIUS_RATIO,
                center = center,
            )
        }
        node.label?.let { label ->
            val layout = textMeasurer.measure(label, style = labelStyle)
            drawText(
                textLayoutResult = layout,
                topLeft = Offset(center.x + radius + LABEL_OFFSET, center.y - layout.size.height / 2f),
            )
        }
    }
}

@Preview
@Composable
private fun TactileMiniMapPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            TactileMiniMap(
                uiModel = MiniMapUiModel(
                    title = "한빛역 · 점자 블럭 지도",
                    rows = 5,
                    columns = 3,
                    path = listOf(
                        RouteNodeUiModel(row = 2, column = 1, label = "개찰구", state = RouteNodeUiModel.State.PASSED),
                        RouteNodeUiModel(row = 1, column = 1, label = "갈림길", state = RouteNodeUiModel.State.PASSED),
                        RouteNodeUiModel(row = 1, column = 2, label = "2번 출구", state = RouteNodeUiModel.State.HIGHLIGHTED),
                    )
                )
            )
        }
    }
}
