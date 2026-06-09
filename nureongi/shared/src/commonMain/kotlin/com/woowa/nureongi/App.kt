package com.woowa.nureongi

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.woowa.nureongi.ui.model.PlaceUiModel
import com.woowa.nureongi.ui.screen.CurrentLocationSelectionScreen
import com.woowa.nureongi.ui.theme.NureongiTheme

@Composable
@Preview
fun App() {
    NureongiTheme {
        CurrentLocationSelectionScreen(
            locations = previewLocations,
            initialSelectedIndex = 2,
            onBackClick = {},
            onLocationSelected = {},
        )
    }
}

private val previewLocations = listOf(
    PlaceUiModel("1번 출구", "지상 · 버스정류장 방면"),
    PlaceUiModel("2번 출구", "지상 · 광장 방면"),
    PlaceUiModel("개찰구", "대합실 입구"),
    PlaceUiModel("화장실", "대합실 왼쪽"),
    PlaceUiModel("고객센터", "대합실 오른쪽"),
    PlaceUiModel("계단", "승강장 방면 계단"),
    PlaceUiModel("엘리베이터", "휠체어 · 유모차"),
)
