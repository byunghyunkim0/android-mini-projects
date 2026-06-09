package com.woowa.nureongi.ui.destination

import androidx.lifecycle.ViewModel
import com.woowa.nureongi.ui.model.DestinationSelectionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DestinationSelectionViewModel(
    initialState: DestinationSelectionUiState,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState.withValidSelection())
    val uiState = _uiState.asStateFlow()

    fun onDestinationSelected(destinationId: String) {
        _uiState.update { state ->
            if (state.isLoading || state.destinations.none { it.id == destinationId }) {
                state
            } else {
                state.copy(selectedDestinationId = destinationId)
            }
        }
    }

    fun destinationIdForGuidance(): String? {
        return _uiState.value.startGuidanceDestinationId
    }
}

private fun DestinationSelectionUiState.withValidSelection(): DestinationSelectionUiState {
    val validSelectedDestinationId = selectedDestinationId?.takeIf { selectedId ->
        destinations.any { it.id == selectedId }
    }
    return copy(selectedDestinationId = validSelectedDestinationId)
}
