package com.woowa.nureongi.ui.model

object NureongiUiFixtures {
    val DestinationSelection = DestinationSelectionUiState(
        currentLocation = CurrentLocationUiModel(
            nodeId = "gate-01",
            name = "2호선 역삼역 개찰구 앞",
        ),
        destinations = listOf(
            DestinationItemUiModel(
                id = "exit-01",
                place = PlaceUiModel(
                    name = "1번 출구",
                    location = "지상 버스정류장 방면",
                ),
            ),
            DestinationItemUiModel(
                id = "exit-02",
                place = PlaceUiModel(
                    name = "2번 출구",
                    location = "강남역 방면",
                ),
            ),
            DestinationItemUiModel(
                id = "elevator-01",
                place = PlaceUiModel(
                    name = "엘리베이터",
                    location = "지상 연결 엘리베이터",
                ),
            ),
        ),
        selectedDestinationId = "exit-01",
    )

    val MiniMap = MiniMapUiModel(
        title = "점자 블록 지도",
        rows = 5,
        columns = 4,
        path = listOf(
            RouteNodeUiModel(
                row = 3,
                column = 0,
                label = "개찰구",
                state = RouteNodeUiModel.State.HIGHLIGHTED,
            ),
            RouteNodeUiModel(
                row = 3,
                column = 2,
                label = "출구 갈림길",
                state = RouteNodeUiModel.State.PASSED,
            ),
            RouteNodeUiModel(
                row = 1,
                column = 3,
                label = "1번 출구",
                state = RouteNodeUiModel.State.HIGHLIGHTED,
            ),
        ),
    )

    val Guidance = GuidanceUiState(
        destination = DestinationSummaryUiModel(
            id = "exit-01",
            name = "1번 출구",
        ),
        steps = listOf(
            GuidanceStepUiModel(
                id = "step-01",
                actionType = GuidanceActionType.GO_STRAIGHT,
                instructionText = "8m 직진",
                landmarkText = "다음 점형 블록 · 출구 갈림길",
                guideMessage = "앞으로 8미터 직진하세요. 다음 점형 블록에서 멈춰 주세요.",
                remainingDistanceText = "28m",
                remainingBlockCountText = "3개",
            ),
            GuidanceStepUiModel(
                id = "step-02",
                actionType = GuidanceActionType.TURN_RIGHT,
                instructionText = "오른쪽으로 12m 이동",
                landmarkText = "다음 점형 블록 · 계단 앞",
                guideMessage = "오른쪽으로 방향을 틀고 12미터 이동하세요.",
                remainingDistanceText = "20m",
                remainingBlockCountText = "2개",
            ),
            GuidanceStepUiModel(
                id = "step-03",
                actionType = GuidanceActionType.GO_UP,
                instructionText = "계단 올라가기",
                landmarkText = "목적지 · 1번 출구",
                guideMessage = "계단을 올라가면 1번 출구입니다.",
                remainingDistanceText = "8m",
                remainingBlockCountText = "1개",
            ),
        ),
        miniMap = MiniMap,
    )
}
