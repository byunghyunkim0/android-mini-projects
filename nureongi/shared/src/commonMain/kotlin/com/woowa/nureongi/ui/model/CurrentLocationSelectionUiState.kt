package com.woowa.nureongi.ui.model

data class CurrentLocationSelectionUiState(
    val locations: List<PlaceUiModel> = emptyList(),
    val selectedLocationIndex: Int? = null,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val selectedLocation: PlaceUiModel?
        get() = selectedLocationIndex?.let { locations.getOrNull(it) }
}
