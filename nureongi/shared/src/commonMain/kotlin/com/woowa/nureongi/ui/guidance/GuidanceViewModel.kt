package com.woowa.nureongi.ui.guidance

import androidx.lifecycle.ViewModel
import com.woowa.nureongi.ui.model.GuidanceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GuidanceViewModel(
    initialState: GuidanceUiState,
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    fun onNextStep() {
        _uiState.update { state ->
            when {
                state.isArrived -> state
                state.currentStepIndex < state.steps.lastIndex -> {
                    state.copy(currentStepIndex = state.currentStepIndex + 1)
                }
                else -> state.copy(isArrived = true)
            }
        }
    }
}
