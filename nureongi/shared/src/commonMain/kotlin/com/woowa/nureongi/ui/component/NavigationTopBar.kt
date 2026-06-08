package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

/**
 * 길안내 진행 화면 상단의 닫기 버튼 + 목적지 정보 + 진행 단계 바.
 *
 * 안내 종료(닫기) 동작만 콜백으로 받고, 단계 계산이나 화면 전환은 호출 측 책임으로 둔다.
 *
 * @param destinationName 목적지 이름 (예: "2번 출구")
 * @param currentStep 현재 단계 (1부터 시작). 진행 표시줄에는 이 값이 곧
 *   완료된 단계 수(`completedSteps`)로 전달되어, 진행 중인 현재 단계까지 채워진다.
 * @param totalSteps 전체 단계 수
 * @param onCloseClick 닫기 버튼을 눌렀을 때 호출되는 콜백
 */
@Composable
fun NavigationTopBar(
    destinationName: String,
    currentStep: Int,
    totalSteps: Int,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "✕",
                color = NureongiColors.TextPrimary,
                modifier = Modifier
                    .clickable(
                        onClickLabel = "안내 종료",
                        role = Role.Button,
                        onClick = onCloseClick,
                    )
                    .padding(8.dp),
            )
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "목적지",
                    style = NureongiTypography.ItemDescription,
                    color = NureongiColors.TextSecondary,
                )
                Text(
                    text = destinationName,
                    style = NureongiTypography.ItemTitle,
                    color = NureongiColors.TextPrimary,
                    modifier = Modifier.semantics { heading() },
                )
            }
            Text(
                text = "$currentStep / $totalSteps",
                style = NureongiTypography.ItemDescription,
                color = NureongiColors.TextSecondary,
            )
        }
        SegmentedProgressIndicator(
            totalSteps = totalSteps,
            // 진행 중인 현재 단계까지 채워진 것으로 표시한다 (currentStep == completedSteps).
            completedSteps = currentStep,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

/**
 * 뒤로가기 + 화면 제목으로 구성된 단순 상단바 (예: "현재 위치" 선택 화면).
 *
 * @param title 화면 제목
 * @param onBackClick 뒤로가기를 눌렀을 때 호출되는 콜백
 */
@Composable
fun BackNavigationTopBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "‹ 뒤로",
            style = NureongiTypography.ItemDescription,
            color = NureongiColors.Accent,
            modifier = Modifier
                .clickable(
                    onClickLabel = "뒤로 가기",
                    role = Role.Button,
                    onClick = onBackClick,
                )
                .padding(8.dp),
        )
        Text(
            text = title,
            style = NureongiTypography.SectionHeader,
            color = NureongiColors.TextPrimary,
            modifier = Modifier.semantics { heading() },
        )
    }
}

@Preview
@Composable
private fun NavigationTopBarPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            NavigationTopBar(
                destinationName = "2번 출구",
                currentStep = 1,
                totalSteps = 3,
                onCloseClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun BackNavigationTopBarPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            BackNavigationTopBar(title = "현재 위치", onBackClick = {})
        }
    }
}
