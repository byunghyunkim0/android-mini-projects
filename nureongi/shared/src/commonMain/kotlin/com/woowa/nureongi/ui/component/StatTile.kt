package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

/**
 * "20m" / "남은 거리"처럼, 핵심 수치 하나와 그 설명을 세로로 보여주는 타일.
 *
 * 값 계산이나 단위 포맷팅은 호출 측(화면/매퍼)에서 끝낸 문자열을 그대로
 * 표시만 한다. 값과 설명을 하나의 의미 단위로 묶어 스크린 리더에 전달한다.
 *
 * @param value 강조해서 보여줄 값 (예: "20m")
 * @param label 값을 설명하는 한 줄 문구 (예: "남은 거리")
 */
@Composable
fun StatTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(NureongiColors.Surface)
            .padding(16.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "$label $value"
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = value,
            style = NureongiTypography.SectionHeader,
            color = NureongiColors.Accent,
        )
        Text(
            text = label,
            style = NureongiTypography.ItemDescription,
            color = NureongiColors.TextSecondary,
        )
    }
}

@Preview
@Composable
private fun StatTilePreview() {
    NureongiTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatTile(value = "20m", label = "남은 거리", modifier = Modifier.weight(1f))
            StatTile(value = "2개", label = "남은 점형 블럭", modifier = Modifier.weight(1f))
        }
    }
}
