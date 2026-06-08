package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme

/**
 * 안내 진행 단계를 가로로 분할된 막대로 보여주는 진행 표시줄.
 *
 * 전체 단계 수와 완료된 단계 수만 받아 그리며, 진행률 계산이나 단계 전환 로직은
 * 갖지 않는다. 시각 정보와 동일한 내용을 `progressBarRangeInfo` 시맨틱으로도 제공해
 * 스크린 리더가 "3단계 중 1단계"처럼 안내할 수 있게 한다.
 *
 * @param totalSteps 전체 단계 수
 * @param completedSteps 완료된 단계 수 (0..totalSteps)
 */
@Composable
fun SegmentedProgressIndicator(
    totalSteps: Int,
    completedSteps: Int,
    modifier: Modifier = Modifier,
) {
    require(totalSteps > 0) { "totalSteps 는 1 이상이어야 합니다." }
    val completed = completedSteps.coerceIn(0, totalSteps)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clearAndSetSemantics {
                contentDescription = "전체 ${totalSteps}단계 중 ${completed}단계 진행"
                progressBarRangeInfo = ProgressBarRangeInfo(
                    current = completed.toFloat(),
                    range = 0f..totalSteps.toFloat(),
                    steps = totalSteps - 1,
                )
            },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(totalSteps) { index ->
            val color = if (index < completed) NureongiColors.Accent else NureongiColors.Surface
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(color),
            ) {}
        }
    }
}

@Preview
@Composable
private fun SegmentedProgressIndicatorPreview() {
    NureongiTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            SegmentedProgressIndicator(totalSteps = 3, completedSteps = 1)
        }
    }
}
