package com.woowa.nureongi.ui.model

data class DestinationSelectionUiState(
    val currentLocation: CurrentLocationUiModel? = null,
    val destinations: List<DestinationItemUiModel> = emptyList(),
    val selectedDestinationId: String? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val selectedDestination: DestinationItemUiModel?
        get() = destinations.firstOrNull { it.id == selectedDestinationId }

    val canStartGuidance: Boolean
        get() = !isLoading && currentLocation != null && selectedDestination != null
}

data class CurrentLocationUiModel(
    val nodeId: String,
    val name: String,
)

data class DestinationItemUiModel(
    val id: String,
    val place: PlaceUiModel,
)
