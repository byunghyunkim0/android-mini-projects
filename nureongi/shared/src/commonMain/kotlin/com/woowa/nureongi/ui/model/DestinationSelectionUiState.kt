package com.woowa.nureongi.ui.model

data class DestinationSelectionUiState(
    val currentLocation: CurrentLocationUiModel? = null,
    val destinations: List<DestinationItemUiModel> = emptyList(),
    val selectedDestinationId: String? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val currentLocationName: String
        get() = currentLocation?.name ?: "현재 위치를 선택하세요"

    val selectedDestination: DestinationItemUiModel?
        get() = destinations.firstOrNull { it.id == selectedDestinationId }

    val startGuidanceDestinationId: String?
        get() = if (!isLoading && currentLocation != null) selectedDestination?.id else null

    val canStartGuidance: Boolean
        get() = startGuidanceDestinationId != null

    val startGuidanceButtonText: String
        get() = selectedDestination?.let { "${it.place.name}까지 안내 시작" }
            ?: "목적지를 선택하세요"
}

data class CurrentLocationUiModel(
    val nodeId: String,
    val name: String,
)

data class DestinationItemUiModel(
    val id: String,
    val place: PlaceUiModel,
)

internal val PreviewDestinationSelectionUiState = DestinationSelectionUiState(
    currentLocation = CurrentLocationUiModel(
        nodeId = "gate",
        name = "개찰구",
    ),
    destinations = listOf(
        DestinationItemUiModel("exit-1", PlaceUiModel("1번 출구", "지상 · 버스정류장 방면")),
        DestinationItemUiModel("exit-2", PlaceUiModel("2번 출구", "지상 · 광장 방면")),
        DestinationItemUiModel("restroom", PlaceUiModel("화장실", "대합실 왼쪽")),
        DestinationItemUiModel("service-center", PlaceUiModel("고객센터", "대합실 오른쪽")),
        DestinationItemUiModel("stairs", PlaceUiModel("계단", "승강장 방면 계단")),
    ),
)
