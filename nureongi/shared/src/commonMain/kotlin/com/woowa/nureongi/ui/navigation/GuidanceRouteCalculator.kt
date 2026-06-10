package com.woowa.nureongi.ui.navigation

import com.woowa.nureongi.ui.model.CurrentLocationUiModel
import com.woowa.nureongi.ui.model.DestinationItemUiModel
import com.woowa.nureongi.ui.model.GuidanceStepUiModel
import com.woowa.nureongi.ui.model.GuidanceUiState
import com.woowa.nureongi.ui.model.MiniMapUiModel
import com.woowa.nureongi.ui.model.RouteNodeUiModel

internal fun interface GuidanceRouteCalculator {
    fun calculate(
        currentLocation: CurrentLocationUiModel,
        destination: DestinationItemUiModel,
    ): GuidanceRouteCalculationResult
}

internal sealed interface GuidanceRouteCalculationResult {
    data class Success(
        val guidanceState: GuidanceUiState,
    ) : GuidanceRouteCalculationResult

    data class Failure(
        val message: String,
    ) : GuidanceRouteCalculationResult
}

internal class InMemoryGuidanceRouteCalculator : GuidanceRouteCalculator {
    override fun calculate(
        currentLocation: CurrentLocationUiModel,
        destination: DestinationItemUiModel,
    ): GuidanceRouteCalculationResult {
        if (currentLocation.nodeId == destination.id) {
            return GuidanceRouteCalculationResult.Failure(
                message = "현재 위치와 목적지가 같습니다. 다른 목적지를 선택해 주세요.",
            )
        }

        val start = stationNodes[currentLocation.nodeId]
        val end = stationNodes[destination.id]
        if (start == null || end == null) {
            return GuidanceRouteCalculationResult.Failure(
                message = "경로를 찾을 수 없습니다. 현재 위치나 목적지를 다시 선택해 주세요.",
            )
        }

        val path = buildList {
            add(start.copy(label = currentLocation.name))
            stationNodes["gate"]
                ?.takeIf { junction -> junction.position != start.position && junction.position != end.position }
                ?.let(::add)
            add(end.copy(label = destination.place.name))
        }
        val steps = path.mapIndexed { index, node ->
            val remainingSegments = path.lastIndex - index
            GuidanceStepUiModel(
                instruction = if (index == path.lastIndex) {
                    "${destination.place.name} 방향으로 직진"
                } else {
                    "다음 점형 블록까지 직진"
                },
                landmark = node.label,
                guideMessage = if (index == path.lastIndex) {
                    "${destination.place.name}까지 직진하면 목적지에 도착합니다."
                } else {
                    "다음 점형 블록까지 직진하세요."
                },
                remainingDistanceText = "${(remainingSegments + 1) * SegmentDistanceMeters}m",
                remainingTactileBlockText = "${remainingSegments}개",
                actionButtonText = if (index == path.lastIndex) {
                    "목적지 도착 ›"
                } else {
                    "다음 점형 블록 도착 ›"
                },
            )
        }

        return GuidanceRouteCalculationResult.Success(
            guidanceState = GuidanceUiState(
                destinationName = destination.place.name,
                steps = steps,
                arrivalGuidance = GuidanceStepUiModel(
                    instruction = "도착",
                    landmark = destination.place.name,
                    guideMessage = "${destination.place.name}에 도착했습니다. 안내를 종료하려면 안내 종료 버튼을 누르세요.",
                    remainingDistanceText = "0m",
                    remainingTactileBlockText = "0개",
                    actionButtonText = "안내 종료",
                ),
                miniMap = MiniMapUiModel(
                    title = "판교역 · 점자 블록 지도",
                    rows = MapRows,
                    columns = MapColumns,
                    path = path.map { node ->
                        RouteNodeUiModel(
                            row = node.position.row,
                            column = node.position.column,
                            label = node.label,
                        )
                    },
                ),
            ),
        )
    }
}

private data class StationNode(
    val position: StationNodePosition,
    val label: String,
)

private data class StationNodePosition(
    val row: Int,
    val column: Int,
)

private val stationNodes = mapOf(
    "exit-1" to StationNode(StationNodePosition(0, 0), "1번 출구"),
    "exit-2" to StationNode(StationNodePosition(0, 2), "2번 출구"),
    "gate" to StationNode(StationNodePosition(2, 1), "개찰구"),
    "restroom" to StationNode(StationNodePosition(2, 0), "화장실"),
    "service-center" to StationNode(StationNodePosition(2, 2), "고객센터"),
    "stairs" to StationNode(StationNodePosition(4, 0), "계단"),
    "elevator" to StationNode(StationNodePosition(4, 2), "엘리베이터"),
)

private const val SegmentDistanceMeters = 8
private const val MapRows = 5
private const val MapColumns = 3
