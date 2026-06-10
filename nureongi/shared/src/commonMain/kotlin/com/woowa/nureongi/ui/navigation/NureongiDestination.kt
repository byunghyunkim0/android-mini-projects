package com.woowa.nureongi.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal data object CurrentLocationDestination

@Serializable
internal data object DestinationSelectionDestination

@Serializable
internal data class GuidanceDestination(
    val currentLocationId: String,
    val destinationId: String,
)
