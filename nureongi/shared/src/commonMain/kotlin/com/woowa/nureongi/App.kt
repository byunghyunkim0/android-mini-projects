package com.woowa.nureongi

import androidx.compose.runtime.Composable
import com.woowa.nureongi.ui.model.PreviewGuidanceUiState
import com.woowa.nureongi.ui.screen.GuidanceRoute
import com.woowa.nureongi.ui.theme.NureongiTheme

@Composable
fun App() {
    NureongiTheme {
        GuidanceRoute(
            initialState = PreviewGuidanceUiState,
            onCloseGuidance = {},
            onVoiceGuideClick = {},
        )
    }
}
