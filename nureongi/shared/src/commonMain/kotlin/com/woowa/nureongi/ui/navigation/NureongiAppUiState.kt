package com.woowa.nureongi.ui.navigation

import com.woowa.nureongi.ui.model.CurrentLocationSelectionUiState
import com.woowa.nureongi.ui.model.DestinationSelectionUiState
import com.woowa.nureongi.ui.model.GuidanceUiState
import com.woowa.nureongi.ui.model.PangyoStationUiData

internal data class NureongiAppUiState(
    val currentLocationState: CurrentLocationSelectionUiState = initialCurrentLocationState,
    val destinationState: DestinationSelectionUiState = initialDestinationState,
    val guidanceState: GuidanceUiState? = null,
)

private val initialCurrentLocationState = CurrentLocationSelectionUiState(
    locations = PangyoStationUiData.currentLocations,
)

private val initialDestinationState = DestinationSelectionUiState(
    destinations = PangyoStationUiData.destinations,
)
