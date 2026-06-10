package com.woowa.nureongi.ui.model

data class CurrentLocationSelectionUiState(
    val locations: List<CurrentLocationItemUiModel> = emptyList(),
    val selectedLocationId: String? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val selectedLocation: CurrentLocationItemUiModel?
        get() = locations.firstOrNull { it.id == selectedLocationId }

    val selectedLocationIdForResult: String?
        get() = if (isLoading) null else selectedLocation?.id
}

data class CurrentLocationItemUiModel(
    val id: String,
    val place: PlaceUiModel,
)

internal val PreviewCurrentLocationSelectionUiState = CurrentLocationSelectionUiState(
    locations = listOf(
        CurrentLocationItemUiModel("exit-1", PlaceUiModel("1번 출구", "지상 · 버스정류장 방면")),
        CurrentLocationItemUiModel("exit-2", PlaceUiModel("2번 출구", "지상 · 광장 방면")),
        CurrentLocationItemUiModel("gate", PlaceUiModel("개찰구", "대합실 입구")),
        CurrentLocationItemUiModel("restroom", PlaceUiModel("화장실", "대합실 왼쪽")),
        CurrentLocationItemUiModel("service-center", PlaceUiModel("고객센터", "대합실 오른쪽")),
        CurrentLocationItemUiModel("stairs", PlaceUiModel("계단", "승강장 방면 계단")),
        CurrentLocationItemUiModel("elevator", PlaceUiModel("엘리베이터", "휠체어 · 유모차")),
    ),
    selectedLocationId = "gate",
)
