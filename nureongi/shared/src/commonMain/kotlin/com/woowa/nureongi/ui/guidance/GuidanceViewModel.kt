package com.woowa.nureongi.ui.guidance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.woowa.nureongi.ui.model.GuidanceUiState

class GuidanceViewModel(
    initialState: GuidanceUiState,
) : ViewModel() {
    var uiState: GuidanceUiState by mutableStateOf(initialState)
        private set

    fun onNextStep() {
        if (uiState.isArrived) return

        uiState = if (uiState.currentStepIndex < uiState.steps.lastIndex) {
            uiState.copy(currentStepIndex = uiState.currentStepIndex + 1)
        } else {
            uiState.copy(isArrived = true)
        }
    }
}
