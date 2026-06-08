package com.woowa.nureongi.ui.model

data class GuidanceUiState(
    val destination: DestinationSummaryUiModel,
    val steps: List<GuidanceStepUiModel>,
    val currentStepIndex: Int = 0,
    val miniMap: MiniMapUiModel = MiniMapUiModel.Empty,
    val isArrived: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val totalStepCount: Int
        get() = steps.size

    val hasSteps: Boolean
        get() = steps.isNotEmpty()

    val safeCurrentStepIndex: Int
        get() = when {
            steps.isEmpty() -> 0
            currentStepIndex < 0 -> 0
            currentStepIndex >= steps.size -> steps.lastIndex
            else -> currentStepIndex
        }

    val currentStep: GuidanceStepUiModel?
        get() = steps.getOrNull(safeCurrentStepIndex)

    val displayStepNumber: Int
        get() = if (steps.isEmpty()) 0 else safeCurrentStepIndex + 1

    val completedStepCount: Int
        get() = when {
            steps.isEmpty() -> 0
            isArrived -> steps.size
            else -> displayStepNumber
        }

    val progressRatio: Float
        get() = when {
            steps.isEmpty() -> 0f
            isArrived -> 1f
            else -> displayStepNumber.toFloat() / steps.size.toFloat()
        }

    val isLastStep: Boolean
        get() = steps.isNotEmpty() && safeCurrentStepIndex == steps.lastIndex

    val remainingDistanceText: String
        get() = if (isArrived) "0m" else currentStep?.remainingDistanceText ?: "0m"

    val remainingBlockCountText: String
        get() = if (isArrived) "0개" else currentStep?.remainingBlockCountText ?: "0개"

    val primaryButtonText: String
        get() = when {
            isArrived -> "안내 종료"
            isLastStep -> "목적지 도착"
            else -> "다음 점형 블록 도착"
        }

    val canMoveToNextStep: Boolean
        get() = !isLoading && hasSteps && !isArrived

    fun moveToNextStep(): GuidanceUiState =
        when {
            steps.isEmpty() -> this
            isArrived -> this
            isLastStep -> copy(isArrived = true)
            else -> copy(currentStepIndex = safeCurrentStepIndex + 1)
        }

    companion object {
        val Empty = GuidanceUiState(
            destination = DestinationSummaryUiModel.Empty,
            steps = emptyList(),
        )
    }
}

data class DestinationSummaryUiModel(
    val id: String,
    val place: PlaceUiModel,
    val label: String = "목적지",
    val accessibilityLabel: String = if (place.location.isBlank()) "$label ${place.name}" else "$label ${place.name}, ${place.location}",
) {
    constructor(
        id: String,
        title: String,
        subtitle: String = "",
        label: String = "목적지",
    ) : this(
        id = id,
        place = PlaceUiModel(name = title, location = subtitle),
        label = label,
    )

    val title: String
        get() = place.name

    val subtitle: String
        get() = place.location

    companion object {
        val Empty = DestinationSummaryUiModel(
            id = "",
            title = "목적지 미선택",
        )
    }
}

data class GuidanceStepUiModel(
    val id: String,
    val sequence: Int,
    val currentLocationName: String,
    val actionType: GuidanceActionType,
    val actionLabel: String,
    val distanceText: String,
    val nextLocationName: String,
    val instruction: String,
    val remainingDistanceText: String,
    val remainingBlockCountText: String,
    val accessibilityLabel: String = buildString {
        append(sequence).append("번째 안내, ")
        append(currentLocationName).append("에서 ")
        append(actionLabel).append(" ")
        append(distanceText).append(". ")
        append("다음 지점은 ").append(nextLocationName).append(". ")
        append(instruction)
    },
) {
    val instructionText: String
        get() = listOf(distanceText, actionLabel)
            .filter { it.isNotBlank() }
            .joinToString(separator = " ")

    val landmarkText: String
        get() = "다음 점형 블록 · $nextLocationName"

    val guideMessage: String
        get() = instruction
}

enum class GuidanceActionType {
    START,
    GO_STRAIGHT,
    TURN_LEFT,
    TURN_RIGHT,
    GO_UP,
    GO_DOWN,
    ARRIVE,
}
