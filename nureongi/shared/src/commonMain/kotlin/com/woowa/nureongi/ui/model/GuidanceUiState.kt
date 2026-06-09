package com.woowa.nureongi.ui.model

data class GuidanceUiState(
    val destinationName: String,
    val currentStep: Int,
    val totalSteps: Int,
    val currentGuidance: GuidanceStepUiModel,
    val remainingDistanceText: String,
    val remainingTactileBlockText: String,
    val nextButtonText: String,
    val miniMap: MiniMapUiModel,
)

data class GuidanceStepUiModel(
    val instruction: String,
    val landmark: String,
    val guideMessage: String,
)

internal val PreviewGuidanceUiState = GuidanceUiState(
    destinationName = "2번 출구",
    currentStep = 1,
    totalSteps = 3,
    currentGuidance = GuidanceStepUiModel(
        instruction = "8m 직진",
        landmark = "다음 점형 블록 · 출구 갈림길",
        guideMessage = "2번 출구까지 안내를 시작합니다. 앞으로 8미터 직진하세요. 8미터 앞에 갈림길이 있습니다.",
    ),
    remainingDistanceText = "20m",
    remainingTactileBlockText = "2개",
    nextButtonText = "다음 점형 블록 도착 ›",
    miniMap = MiniMapUiModel(
        title = "한빛역 · 점자 블럭 지도",
        rows = 5,
        columns = 3,
        path = listOf(
            RouteNodeUiModel(row = 2, column = 1, label = "개찰구", state = RouteNodeUiModel.State.HIGHLIGHTED), // 출발지이자 현재 위치
            RouteNodeUiModel(row = 1, column = 1, label = "갈림길", state = RouteNodeUiModel.State.NEUTRAL),
            RouteNodeUiModel(row = 1, column = 2, label = "2번 출구", state = RouteNodeUiModel.State.NEUTRAL), // 목적지 (마지막 노드)
        )
    )
)
