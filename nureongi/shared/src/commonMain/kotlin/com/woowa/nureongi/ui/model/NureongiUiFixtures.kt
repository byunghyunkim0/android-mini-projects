package com.woowa.nureongi.ui.model

object NureongiUiFixtures {
    val DestinationSelection = DestinationSelectionUiState(
        stationName = "역삼역",
        currentLocation = CurrentLocationUiModel(
            nodeId = "gate-01",
            locationName = "2호선 역삼역 개찰구 앞",
        ),
        destinations = listOf(
            DestinationItemUiModel(
                id = "exit-01",
                title = "1번 출구",
                subtitle = "지상 버스정류장 방면",
                distanceText = "28m",
                blockCountText = "점형 블록 3개",
                isSelected = true,
                isRecommended = true,
                badgeText = "추천",
            ),
            DestinationItemUiModel(
                id = "exit-02",
                title = "2번 출구",
                subtitle = "강남역 방면",
                distanceText = "36m",
                blockCountText = "점형 블록 4개",
            ),
            DestinationItemUiModel(
                id = "elevator-01",
                title = "엘리베이터",
                subtitle = "지상 연결 엘리베이터",
                distanceText = "42m",
                blockCountText = "점형 블록 5개",
            ),
        ),
    )

    val MiniMap = MiniMapUiModel(
        title = "점자 블록 지도",
        rows = 5,
        columns = 4,
        nodes = listOf(
            MiniMapNodeUiModel(
                id = "gate-01",
                label = "개찰구",
                row = 3,
                column = 0,
                type = MiniMapNodeType.CURRENT,
            ),
            MiniMapNodeUiModel(
                id = "junction-01",
                label = "출구 갈림길",
                row = 3,
                column = 2,
            ),
            MiniMapNodeUiModel(
                id = "exit-01",
                label = "1번 출구",
                row = 1,
                column = 3,
                type = MiniMapNodeType.DESTINATION,
            ),
        ),
        edges = listOf(
            MiniMapEdgeUiModel(
                id = MiniMapEdgeUiModel.edgeId("gate-01", "junction-01"),
                fromNodeId = "gate-01",
                toNodeId = "junction-01",
                isHighlighted = true,
            ),
            MiniMapEdgeUiModel(
                id = MiniMapEdgeUiModel.edgeId("junction-01", "exit-01"),
                fromNodeId = "junction-01",
                toNodeId = "exit-01",
                isHighlighted = true,
            ),
        ),
        pathNodeIds = listOf("gate-01", "junction-01", "exit-01"),
        currentNodeId = "gate-01",
        destinationNodeId = "exit-01",
        accessibilityLabel = "현재 위치 개찰구에서 출구 갈림길을 지나 1번 출구까지 이동합니다.",
    )

    val Guidance = GuidanceUiState(
        destination = DestinationSummaryUiModel(
            id = "exit-01",
            title = "1번 출구",
            subtitle = "지상 버스정류장 방면",
        ),
        steps = listOf(
            GuidanceStepUiModel(
                id = "step-01",
                sequence = 1,
                currentLocationName = "개찰구 앞",
                actionType = GuidanceActionType.GO_STRAIGHT,
                actionLabel = "직진",
                distanceText = "8m",
                nextLocationName = "출구 갈림길",
                instruction = "앞으로 8미터 직진하세요. 다음 점형 블록에서 멈춰 주세요.",
                remainingDistanceText = "28m",
                remainingBlockCountText = "3개",
            ),
            GuidanceStepUiModel(
                id = "step-02",
                sequence = 2,
                currentLocationName = "출구 갈림길",
                actionType = GuidanceActionType.TURN_RIGHT,
                actionLabel = "오른쪽으로 이동",
                distanceText = "12m",
                nextLocationName = "계단 앞",
                instruction = "오른쪽으로 방향을 틀고 12미터 이동하세요.",
                remainingDistanceText = "20m",
                remainingBlockCountText = "2개",
            ),
            GuidanceStepUiModel(
                id = "step-03",
                sequence = 3,
                currentLocationName = "계단 앞",
                actionType = GuidanceActionType.GO_UP,
                actionLabel = "계단 올라가기",
                distanceText = "8m",
                nextLocationName = "1번 출구",
                instruction = "계단을 올라가면 1번 출구입니다.",
                remainingDistanceText = "8m",
                remainingBlockCountText = "1개",
            ),
        ),
        currentStepIndex = 0,
        miniMap = MiniMap,
    )
}
