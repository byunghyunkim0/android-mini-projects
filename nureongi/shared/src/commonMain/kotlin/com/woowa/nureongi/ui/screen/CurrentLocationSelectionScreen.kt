package com.woowa.nureongi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.component.BackNavigationTopBar
import com.woowa.nureongi.ui.component.PlaceListItem
import com.woowa.nureongi.ui.model.PlaceUiModel
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

@Composable
fun CurrentLocationSelectionScreen(
    locations: List<PlaceUiModel>,
    onBackClick: () -> Unit,
    onLocationSelected: (PlaceUiModel) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int? = null,
) {
    var selectedIndex by remember(initialSelectedIndex) { mutableStateOf(initialSelectedIndex) }

    CurrentLocationSelectionContent(
        locations = locations,
        selectedIndex = selectedIndex,
        onBackClick = onBackClick,
        onSelectLocation = { index ->
            selectedIndex = index
            locations.getOrNull(index)?.let(onLocationSelected)
        },
        modifier = modifier,
    )
}

@Composable
private fun CurrentLocationSelectionContent(
    locations: List<PlaceUiModel>,
    selectedIndex: Int?,
    onBackClick: () -> Unit,
    onSelectLocation: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NureongiColors.Background)
            .safeContentPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            CurrentLocationSelectionHeader(onBackClick = onBackClick)
            LocationList(
                locations = locations,
                selectedIndex = selectedIndex,
                onSelectLocation = onSelectLocation,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun CurrentLocationSelectionHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        BackNavigationTopBar(
            title = "현재 위치",
            onBackClick = onBackClick,
        )
        Text(
            text = "지금 서 있는 점형 블럭을 선택하세요.",
            style = NureongiTypography.ItemTitle,
            color = NureongiColors.TextSecondary,
        )
    }
}

@Composable
private fun LocationList(
    locations: List<PlaceUiModel>,
    selectedIndex: Int?,
    onSelectLocation: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(locations) { index, location ->
            PlaceListItem(
                place = location,
                selected = index == selectedIndex,
                onClick = { onSelectLocation(index) },
            )
        }
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

@Preview
@Composable
private fun CurrentLocationSelectionContentPreview() {
    NureongiTheme {
        CurrentLocationSelectionContent(
            locations = previewLocations,
            selectedIndex = 2,
            onBackClick = {},
            onSelectLocation = {},
        )
    }
}
