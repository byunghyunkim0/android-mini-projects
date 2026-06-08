package com.woowa.nureongi.ui.model

data class DestinationSelectionUiState(
    val stationName: String,
    val currentLocation: CurrentLocationUiModel,
    val destinations: List<DestinationItemUiModel>,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val selectedDestination: DestinationItemUiModel?
        get() = destinations.firstOrNull { it.isSelected }

    val selectedDestinationId: String?
        get() = selectedDestination?.id

    val canStartGuidance: Boolean
        get() = !isLoading && selectedDestination != null

    val startGuidanceButtonText: String
        get() = selectedDestination?.let { "${it.name}까지 안내 시작" } ?: "목적지를 선택해 주세요"

    val hasDestinations: Boolean
        get() = destinations.isNotEmpty()

    val currentLocationName: String
        get() = currentLocation.locationName

    fun selectDestination(destinationId: String): DestinationSelectionUiState =
        copy(
            destinations = destinations.map { destination ->
                destination.copy(isSelected = destination.id == destinationId)
            },
            error = null,
        )

    companion object {
        val Empty = DestinationSelectionUiState(
            stationName = "",
            currentLocation = CurrentLocationUiModel.Empty,
            destinations = emptyList(),
        )
    }
}

data class CurrentLocationUiModel(
    val nodeId: String,
    val locationName: String,
    val label: String = "현재 위치",
    val changeButtonText: String = "변경",
    val accessibilityLabel: String = "$label, $locationName",
) {
    companion object {
        val Empty = CurrentLocationUiModel(
            nodeId = "",
            locationName = "출발 위치를 선택해 주세요",
        )
    }
}

data class DestinationItemUiModel(
    val id: String,
    val place: PlaceUiModel,
    val distanceText: String,
    val blockCountText: String,
    val isSelected: Boolean = false,
    val isRecommended: Boolean = false,
    val badgeText: String? = null,
    val accessibilityLabel: String = buildString {
        append(place.name)
        if (place.location.isNotBlank()) append(", ").append(place.location)
        if (distanceText.isNotBlank()) append(", 거리 ").append(distanceText)
        if (blockCountText.isNotBlank()) append(", ").append(blockCountText)
        if (isSelected) append(", 선택됨")
    },
) {
    constructor(
        id: String,
        title: String,
        subtitle: String,
        distanceText: String,
        blockCountText: String,
        isSelected: Boolean = false,
        isRecommended: Boolean = false,
        badgeText: String? = null,
    ) : this(
        id = id,
        place = PlaceUiModel(name = title, location = subtitle),
        distanceText = distanceText,
        blockCountText = blockCountText,
        isSelected = isSelected,
        isRecommended = isRecommended,
        badgeText = badgeText,
    )

    val name: String
        get() = place.name

    val location: String
        get() = place.location

    val title: String
        get() = name

    val subtitle: String
        get() = location

    val metadataText: String
        get() = listOf(distanceText, blockCountText)
            .filter { it.isNotBlank() }
            .joinToString(separator = " · ")
}
