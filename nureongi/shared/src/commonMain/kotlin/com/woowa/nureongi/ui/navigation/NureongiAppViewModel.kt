package com.woowa.nureongi.ui.navigation

import androidx.lifecycle.ViewModel
import com.woowa.nureongi.domain.data.PangyoStationMapData
import com.woowa.nureongi.domain.data.StationMapDataSource
import com.woowa.nureongi.ui.model.CurrentLocationUiModel
import com.woowa.nureongi.ui.model.GuidanceUiState
import com.woowa.nureongi.ui.model.UiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class NureongiAppViewModel(
    mapDataSource: StationMapDataSource = PangyoStationMapData,
    initialState: NureongiAppUiState? = null,
    routeCalculator: GuidanceRouteCalculator? = null,
) : ViewModel() {
    private val mapData = mapDataSource.getMapData()
    private val routeCalculator = routeCalculator ?: MapGuidanceRouteCalculator(mapData)
    private val _uiState = MutableStateFlow(
        initialState ?: mapData.toInitialAppUiState(),
    )
    val uiState = _uiState.asStateFlow()

    fun onLocationSelected(locationId: String): CurrentLocationUiModel? {
        val state = _uiState.value
        if (state.currentLocationState.isLoading) {
            return null
        }
        val selectedLocation = state.currentLocationState.locations
            .firstOrNull { location -> location.id == locationId }
            ?: return null
        val currentLocation = CurrentLocationUiModel(
            nodeId = selectedLocation.id,
            name = selectedLocation.place.name,
        )
        _uiState.value = state.copy(
            currentLocationState = state.currentLocationState.copy(
                selectedLocationId = locationId,
                error = null,
            ),
            destinationState = state.destinationState.copy(
                currentLocation = currentLocation,
                selectedDestinationId = state.destinationState.selectedDestinationId
                    ?.takeUnless { destinationId -> destinationId == locationId },
                error = null,
            ),
            guidanceState = null,
        )
        return currentLocation
    }

    fun onDestinationSelected(destinationId: String) {
        _uiState.update { state ->
            if (
                state.destinationState.isLoading ||
                state.destinationState.destinations.none { destination -> destination.id == destinationId }
            ) {
                state
            } else {
                state.copy(
                    destinationState = state.destinationState.copy(
                        selectedDestinationId = destinationId,
                        error = null,
                    ),
                )
            }
        }
    }

    fun onStartGuidance(): GuidanceNavRoute? {
        val state = _uiState.value
        if (state.destinationState.isLoading) {
            return null
        }
        val currentLocation = state.destinationState.currentLocation
            ?: return updateRouteError(state, "현재 위치를 먼저 선택해 주세요.")
        val destination = state.destinationState.selectedDestination
            ?: return updateRouteError(state, "목적지를 먼저 선택해 주세요.")

        return when (val result = routeCalculator.calculate(currentLocation, destination)) {
            is GuidanceRouteCalculationResult.Success -> {
                _uiState.value = state.copy(
                    destinationState = state.destinationState.copy(error = null),
                    guidanceState = result.guidanceState,
                )
                GuidanceNavRoute(
                    currentLocationId = currentLocation.nodeId,
                    destinationId = destination.id,
                )
            }

            is GuidanceRouteCalculationResult.Failure -> updateRouteError(state, result.message)
        }
    }

    fun onNextGuidanceStep() {
        _uiState.update { state ->
            state.copy(guidanceState = state.guidanceState?.nextStep())
        }
    }

    fun onGuidanceDestinationEntered(
        currentLocationId: String,
        destinationId: String,
    ) {
        if (_uiState.value.guidanceState != null) {
            return
        }

        onLocationSelected(currentLocationId)
        onDestinationSelected(destinationId)
        onStartGuidance()
    }

    private fun updateRouteError(
        state: NureongiAppUiState,
        message: String,
    ): GuidanceNavRoute? {
        _uiState.value = state.withRouteError(message)
        return null
    }
}

private fun NureongiAppUiState.withRouteError(message: String): NureongiAppUiState {
    return copy(
        destinationState = destinationState.copy(error = UiError(message)),
        guidanceState = null,
    )
}

private fun GuidanceUiState.nextStep(): GuidanceUiState {
    return when {
        isArrived -> this
        currentStepIndex < steps.lastIndex -> copy(currentStepIndex = currentStepIndex + 1)
        else -> copy(isArrived = true)
    }
}
