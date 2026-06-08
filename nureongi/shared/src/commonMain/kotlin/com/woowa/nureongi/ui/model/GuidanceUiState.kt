package com.woowa.nureongi.ui.model

data class GuidanceUiState(
    val destination: DestinationSummaryUiModel,
    val steps: List<GuidanceStepUiModel> = emptyList(),
    val currentStepIndex: Int = 0,
    val miniMap: MiniMapUiModel? = null,
    val isArrived: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiError? = null,
) {
    val totalStepCount: Int
        get() = steps.size

    val currentStep: GuidanceStepUiModel?
        get() = steps.getOrNull(currentStepIndex)

    val displayStepNumber: Int
        get() = if (currentStep == null) 0 else currentStepIndex + 1

    val isLastStep: Boolean
        get() = steps.isNotEmpty() && currentStepIndex == steps.lastIndex
}

data class DestinationSummaryUiModel(
    val id: String,
    val name: String,
)

data class GuidanceStepUiModel(
    val id: String,
    val actionType: GuidanceActionType,
    val instructionText: String,
    val landmarkText: String,
    val guideMessage: String,
    val remainingDistanceText: String,
    val remainingBlockCountText: String,
)

enum class GuidanceActionType {
    START,
    GO_STRAIGHT,
    TURN_LEFT,
    TURN_RIGHT,
    GO_UP,
    GO_DOWN,
    ARRIVE,
}
