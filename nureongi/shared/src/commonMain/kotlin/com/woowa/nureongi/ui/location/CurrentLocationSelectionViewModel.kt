package com.woowa.nureongi.ui.location

import androidx.lifecycle.ViewModel
import com.woowa.nureongi.ui.model.CurrentLocationSelectionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CurrentLocationSelectionViewModel(
    initialState: CurrentLocationSelectionUiState,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState.withValidSelection())
    val uiState = _uiState.asStateFlow()

    fun onLocationSelected(locationId: String) {
        _uiState.update { state ->
            if (state.isLoading || state.locations.none { it.id == locationId }) {
                state
            } else {
                state.copy(selectedLocationId = locationId)
            }
        }
    }

    fun locationIdForResult(): String? {
        return _uiState.value.selectedLocationIdForResult
    }
}

private fun CurrentLocationSelectionUiState.withValidSelection(): CurrentLocationSelectionUiState {
    val validSelectedLocationId = selectedLocationId?.takeIf { selectedId ->
        locations.any { it.id == selectedId }
    }
    return copy(selectedLocationId = validSelectedLocationId)
}
