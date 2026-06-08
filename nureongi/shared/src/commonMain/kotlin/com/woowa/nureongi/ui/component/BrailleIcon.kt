package com.woowa.nureongi.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme

/** 아이콘 한 변 대비 상하/좌우 여백 비율. docs/ui 스크린샷에 맞춰 시각적으로 조정한 값. */
private const val PADDING_RATIO = 0.22f

/** 점(dot) 한 칸 크기 대비 반지름 비율. [PADDING_RATIO] 와 우연히 같은 값일 뿐 서로 무관하다. */
private const val DOT_RADIUS_RATIO = 0.22f

/**
 * 점자 블록을 형상화한 점 패턴 아이콘.
 *
 * 장식 목적의 아이콘이므로 별도의 의미를 스크린 리더에 노출하지 않는다(시맨틱 제거).
 * 색상·배경은 항상 파라미터로 전달받아, 선택 상태 등은 호출 측에서 결정한다(상태 호이스팅).
 *
 * @param dotColor 점(dot) 색상
 * @param backgroundColor 아이콘 배경 색상
 * @param size 아이콘 한 변의 길이
 * @param rows 점 패턴의 행 수
 * @param columns 점 패턴의 열 수
 */
@Composable
fun BrailleIcon(
    modifier: Modifier = Modifier,
    dotColor: Color = NureongiColors.Accent,
    backgroundColor: Color = NureongiColors.Surface,
    size: Dp = 40.dp,
    rows: Int = 3,
    columns: Int = 3,
) {
    Canvas(
        modifier = modifier
            .size(size)
            .clearAndSetSemantics {}
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp)),
    ) {
        val horizontalPadding = this.size.width * PADDING_RATIO
        val verticalPadding = this.size.height * PADDING_RATIO
        val drawableWidth = this.size.width - horizontalPadding * 2
        val drawableHeight = this.size.height - verticalPadding * 2
        val dotRadius = (minOf(drawableWidth / columns, drawableHeight / rows)) * DOT_RADIUS_RATIO

        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val x = horizontalPadding + drawableWidth * (column + 0.5f) / columns
                val y = verticalPadding + drawableHeight * (row + 0.5f) / rows
                drawCircle(color = dotColor, radius = dotRadius, center = Offset(x, y))
            }
        }
    }
}

@Preview
@Composable
private fun BrailleIconPreview() {
    NureongiTheme {
        Row(
            modifier = Modifier
                .background(NureongiColors.Background)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BrailleIcon()
            BrailleIcon(dotColor = NureongiColors.OnAccent, backgroundColor = NureongiColors.Accent)
        }
    }
}
