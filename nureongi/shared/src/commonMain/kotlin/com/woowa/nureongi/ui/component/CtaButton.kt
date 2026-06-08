package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

/**
 * 화면 하단에 고정되는 가로 전체 너비의 CTA 버튼.
 *
 * 활성화 여부와 누르는 동작을 파라미터로만 받고, "언제 활성화할지"는 호출 측(화면)에서
 * 결정한다. 비활성 상태에서는 클릭이 동작하지 않을 뿐 아니라, 스크린 리더에도
 * 비활성 상태로 안내된다.
 *
 * @param text 버튼에 표시할 문구
 * @param onClick 버튼을 눌렀을 때 호출되는 콜백 (활성 상태에서만 호출됨)
 * @param enabled 버튼 활성화 여부. 비활성 시 클릭이 동작하지 않고 스크린 리더에도
 * 비활성 상태로 안내된다. 색상은 자동으로 바뀌지 않으므로, 호출 측에서 활성/비활성
 * 상태에 맞는 [containerColor]/[contentColor] 를 직접 전달해야 한다
 * (예: 비활성 시 [NureongiColors.Disabled]/[NureongiColors.OnDisabled]).
 * @param containerColor 배경색
 * @param contentColor 글자색
 */
@Composable
fun CtaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color,
    height: Dp = 56.dp,
    textStyle: TextStyle = NureongiTypography.ItemTitle,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .clickable(
                enabled = enabled,
                onClickLabel = text,
                role = Role.Button,
                onClick = onClick,
            )
            .semantics { if (!enabled) disabled() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = contentColor,
        )
    }
}

@Preview
@Composable
private fun CtaButtonPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NureongiColors.Background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CtaButton(
                text = "2번 출구까지 안내 시작",
                onClick = {},
                containerColor = NureongiColors.Accent,
                contentColor = NureongiColors.OnAccent,
            )
            CtaButton(
                text = "목적지를 선택하세요",
                onClick = {},
                enabled = false,
                containerColor = NureongiColors.Disabled,
                contentColor = NureongiColors.OnDisabled
            )
            CtaButton(
                text = "새 목적지 안내",
                onClick = {},
                containerColor = NureongiColors.NeutralSurface,
                contentColor = NureongiColors.OnAccent,
            )
        }
    }
}
