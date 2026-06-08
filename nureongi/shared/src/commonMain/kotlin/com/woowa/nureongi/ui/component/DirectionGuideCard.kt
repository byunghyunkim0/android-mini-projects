package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

/**
 * 길안내 중 현재 해야 할 행동을 보여주는 안내 카드.
 *
 * 방향 아이콘은 호출 측에서 `leadingIcon` 슬롯으로 그려 넣도록 위임한다(예: 화살표,
 * 점자 패턴 아이콘 등). 이렇게 하면 이 컴포넌트는 "안내 문구를 어떻게 배치할지"에만
 * 집중하고, 실제로 어떤 아이콘을 쓸지는 화면이 결정한다.
 *
 * 제목과 본문을 합쳐 하나의 의미 단위로 전달해, 스크린 리더가 안내를 끊김 없이
 * 읽도록 한다.
 *
 * @param instruction 굵게 강조되는 핵심 안내 문구 (예: "8m 직진")
 * @param landmark 핵심 안내 문구를 보충하는 한 줄 설명 (예: "다음 점형 블럭 · 출구 갈림길")
 * @param guideMessage 상세 안내 문장
 * @param leadingIcon 카드 좌측에 그려질 아이콘 슬롯
 */
@Composable
fun DirectionGuideCard(
    instruction: String,
    landmark: String,
    guideMessage: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(NureongiColors.Surface)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = "$instruction. $landmark"
                heading()
            },
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(NureongiColors.Accent),
                contentAlignment = Alignment.Center,
            ) {
                leadingIcon()
            }
            Column {
                Text(
                    text = instruction,
                    style = NureongiTypography.SectionHeader,
                    color = NureongiColors.TextPrimary,
                )
                Text(
                    text = landmark,
                    style = NureongiTypography.ItemDescription,
                    color = NureongiColors.TextSecondary,
                )
            }
        }
        Text(
            text = guideMessage,
            style = NureongiTypography.ItemTitle,
            color = NureongiColors.TextPrimary,
        )
    }
}

@Preview
@Composable
private fun DirectionGuideCardPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            DirectionGuideCard(
                instruction = "8m 직진",
                landmark = "다음 점형 블럭 · 출구 갈림길",
                guideMessage = "2번 출구까지 안내를 시작합니다. 앞으로 8미터 직진하세요. " +
                    "8미터 앞에 갈림길이 있습니다.",
                leadingIcon = {
                    BrailleIcon(dotColor = NureongiColors.OnAccent, backgroundColor = NureongiColors.Accent)
                },
            )
        }
    }
}
