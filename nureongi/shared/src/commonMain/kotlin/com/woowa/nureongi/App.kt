package com.woowa.nureongi

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.woowa.nureongi.ui.model.PreviewGuidanceUiState
import com.woowa.nureongi.ui.screen.GuidanceScreen
import com.woowa.nureongi.ui.theme.NureongiTheme

@Composable
@Preview
fun App() {
    NureongiTheme {
        GuidanceScreen(
            state = PreviewGuidanceUiState,
            onNextStepClick = {},
            onCloseClick = {},
            onVoiceGuideClick = {},
        )
    }
}
