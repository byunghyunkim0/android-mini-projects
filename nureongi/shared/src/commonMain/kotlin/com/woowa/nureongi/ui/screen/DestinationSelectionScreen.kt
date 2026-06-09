package com.woowa.nureongi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.component.BrailleIcon
import com.woowa.nureongi.ui.component.CtaButton
import com.woowa.nureongi.ui.component.CurrentLocationBar
import com.woowa.nureongi.ui.component.PlaceListItem
import com.woowa.nureongi.ui.model.PlaceUiModel
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

@Composable
fun DestinationSelectionScreen(
    currentLocationName: String,
    destinations: List<PlaceUiModel>,
    onChangeLocationClick: () -> Unit,
    onStartNavigation: (PlaceUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    DestinationSelectionContent(
        currentLocationName = currentLocationName,
        destinations = destinations,
        selectedIndex = selectedIndex,
        onSelectDestination = { index ->
            selectedIndex = index
        },
        onChangeLocationClick = onChangeLocationClick,
        onStartNavigation = onStartNavigation,
        modifier = modifier,
    )
}

@Composable
private fun DestinationSelectionContent(
    currentLocationName: String,
    destinations: List<PlaceUiModel>,
    selectedIndex: Int?,
    onSelectDestination: (Int) -> Unit,
    onChangeLocationClick: () -> Unit,
    onStartNavigation: (PlaceUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedDestination = selectedIndex?.let { destinations.getOrNull(it) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NureongiColors.Background)
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            DestinationSelectionHeader()
            CurrentLocationBar(
                locationName = currentLocationName,
                onChangeClick = onChangeLocationClick,
            )
            DestinationList(
                modifier = Modifier.weight(1f),
                destinations = destinations,
                selectedIndex = selectedIndex,
                onSelectDestination = onSelectDestination,
            )
        }

        DestinationSelectionCtaButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(72.dp),
            selectedDestination = selectedDestination,
            onStartNavigation = onStartNavigation,
        )
    }
}

@Composable
private fun DestinationSelectionHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrailleIcon(
            dotColor = NureongiColors.Accent,
            backgroundColor = NureongiColors.Background,
            modifier = Modifier.size(65.dp)
        )
        Column {
            Text(
                text = "누렁이",
                style = NureongiTypography.SectionHeader,
                color = NureongiColors.TextPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = "점자 블록 길안내",
                style = NureongiTypography.ItemDescription,
                color = NureongiColors.Accent,
            )
        }
    }
}

@Composable
private fun DestinationList(
    modifier: Modifier = Modifier,
    destinations: List<PlaceUiModel>,
    selectedIndex: Int?,
    onSelectDestination: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "어디로 갈까요?",
                style = NureongiTypography.SectionHeader,
                color = NureongiColors.TextPrimary,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = "목적지를 선택하세요.",
                style = NureongiTypography.ItemDescription,
                color = NureongiColors.TextSecondary,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(destinations) { index, destination ->
                PlaceListItem(
                    place = destination,
                    selected = index == selectedIndex,
                    onClick = { onSelectDestination(index) },
                )
            }
        }
    }
}

@Composable
private fun DestinationSelectionCtaButton(
    modifier: Modifier = Modifier,
    selectedDestination: PlaceUiModel?,
    onStartNavigation: (PlaceUiModel) -> Unit,
) {
    if (selectedDestination != null) {
        CtaButton(
            text = "${selectedDestination.name}까지 안내 시작",
            onClick = { onStartNavigation(selectedDestination) },
            containerColor = NureongiColors.Accent,
            contentColor = NureongiColors.OnAccent,
            modifier = modifier,
        )
    } else {
        CtaButton(
            text = "목적지를 선택하세요",
            onClick = {},
            enabled = false,
            containerColor = NureongiColors.Disabled,
            contentColor = NureongiColors.OnDisabled,
            modifier = modifier,
        )
    }
}

private val previewDestinations = listOf(
    PlaceUiModel("1번 출구", "지상 · 버스정류장 방면"),
    PlaceUiModel("2번 출구", "지상 · 광장 방면"),
    PlaceUiModel("화장실", "대합실 왼쪽"),
    PlaceUiModel("고객센터", "대합실 오른쪽"),
    PlaceUiModel("계단", "승강장 방면 계단"),
)

@Preview
@Composable
private fun DestinationSelectionContentPreview() {
    NureongiTheme {
        DestinationSelectionContent(
            currentLocationName = "개찰구",
            destinations = previewDestinations,
            selectedIndex = 1,
            onSelectDestination = {},
            onChangeLocationClick = {},
            onStartNavigation = {},
        )
    }
}
