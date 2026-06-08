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

/**
 * 점형 블럭 경로를 점-선 다이어그램으로 보여주는 지도 카드.
 *
 * `rows` x `columns` 크기의 배경 격자(점선)를 깔고, 그 위에 [path] 로 전달된
 * 노드들을 순서대로 선으로 이어 실제 경로를 강조해 그린다. 좌표 계산·경로 탐색
 * 로직은 갖지 않으며, 이미 계산된 노드 좌표·상태만 표시한다.
 *
 * 시각 정보는 보조 수단이므로, 동일한 내용을 [contentDescription] 으로도 제공해
 * 스크린 리더 사용자가 경로 요약을 들을 수 있게 한다.
 *
 * @param title 지도 제목 (예: "한빛역 · 점자 블럭 지도")
 * @param rows 배경 격자의 행 수
 * @param columns 배경 격자의 열 수
 * @param path 강조해서 그릴 경로 노드 목록 (순서대로 선으로 연결됨)
 */
@Composable
fun RouteMapCard(
    title: String,
    rows: Int,
    columns: Int,
    path: List<RouteNodeUiModel>,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = NureongiTypography.ItemDescription.fontSize,
        color = NureongiColors.Accent,
    )
    val routeDescription = path.joinToString(separator = " → ") { it.label ?: "경유 지점" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(NureongiColors.Surface)
            .padding(20.dp),
    ) {
        Text(
            text = title,
            style = NureongiTypography.ItemDescription,
            color = NureongiColors.TextSecondary,
        )
        Box(modifier = Modifier.padding(top = 12.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .semantics { contentDescription = "$title 경로: $routeDescription" },
            ) {
                if (rows < 2 || columns < 2) return@Canvas

                val cellWidth = size.width / (columns - 1)
                val cellHeight = size.height / (rows - 1)

                drawBackgroundGrid(rows = rows, columns = columns, cellWidth = cellWidth, cellHeight = cellHeight)
                drawHighlightedRoute(
                    path = path,
                    cellWidth = cellWidth,
                    cellHeight = cellHeight,
                    textMeasurer = textMeasurer,
                    labelStyle = labelStyle,
                )
            }
        }
    }
}

/** 격자 상의 (row, column) 위치를 캔버스 좌표로 변환한다. */
private fun cellOffset(row: Int, column: Int, cellWidth: Float, cellHeight: Float): Offset =
    Offset(x = column * cellWidth, y = row * cellHeight)

/** 항상 표시되는 배경 격자(점선 + 회색 점)를 그린다. */
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

/** [path] 를 따라 강조 경로 선·노드·라벨을 그린다. */
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
private fun RouteMapCardPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            RouteMapCard(
                title = "한빛역 · 점자 블럭 지도",
                rows = 5,
                columns = 3,
                path = listOf(
                    RouteNodeUiModel(row = 2, column = 1, label = "개찰구", state = RouteNodeUiModel.State.PASSED),
                    RouteNodeUiModel(row = 1, column = 1, label = "갈림길", state = RouteNodeUiModel.State.PASSED),
                    RouteNodeUiModel(row = 1, column = 2, label = "2번 출구", state = RouteNodeUiModel.State.HIGHLIGHTED),
                ),
            )
        }
    }
}
