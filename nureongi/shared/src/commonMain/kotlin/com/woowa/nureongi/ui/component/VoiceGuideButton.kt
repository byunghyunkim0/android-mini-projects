package com.woowa.nureongi.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import nureongi.shared.generated.resources.Res
import nureongi.shared.generated.resources.ic_volume
import org.jetbrains.compose.resources.painterResource

/**
 * 음성 안내를 다시 들려주는 원형 아이콘 버튼.
 *
 * 음성 재생 로직은 갖지 않고, 눌렀을 때 호출할 콜백만 받는다. 아이콘 모양과 무관하게
 * 항상 "음성 안내 다시 듣기"라는 동일한 의미를 스크린 리더에 전달한다.
 *
 * @param onClick 버튼을 눌렀을 때 호출되는 콜백
 */
@Composable
fun VoiceGuideButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.size(56.dp),
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(NureongiColors.Accent)
            .clickable(
                onClickLabel = "음성 안내 다시 듣기",
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_volume),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun VoiceGuideButtonPreview() {
    NureongiTheme {
        Box(
            modifier = Modifier
                .background(NureongiColors.Background)
                .padding(20.dp),
        ) {
            VoiceGuideButton(onClick = {})
        }
    }
}
