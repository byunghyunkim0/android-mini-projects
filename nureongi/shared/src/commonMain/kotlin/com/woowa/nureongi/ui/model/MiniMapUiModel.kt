package com.woowa.nureongi.ui.model

data class MiniMapUiModel(
    val title: String,
    val rows: Int,
    val columns: Int,
    val nodes: List<MiniMapNodeUiModel>,
    val edges: List<MiniMapEdgeUiModel>,
    val pathNodeIds: List<String>,
    val currentNodeId: String?,
    val destinationNodeId: String?,
    val accessibilityLabel: String,
) {
    val highlightedNodeIds: Set<String>
        get() = pathNodeIds.toSet()

    val highlightedEdgeIds: Set<String>
        get() = pathNodeIds
            .zipWithNext()
            .map { (from, to) -> MiniMapEdgeUiModel.edgeId(from, to) }
            .toSet()

    val routePath: List<RouteNodeUiModel>
        get() = pathNodeIds.mapNotNull { nodeId ->
            nodes.firstOrNull { it.id == nodeId }?.toRouteNodeUiModel(
                currentNodeId = currentNodeId,
                destinationNodeId = destinationNodeId,
            )
        }

    companion object {
        val Empty = MiniMapUiModel(
            title = "점자 블록 지도",
            rows = 0,
            columns = 0,
            nodes = emptyList(),
            edges = emptyList(),
            pathNodeIds = emptyList(),
            currentNodeId = null,
            destinationNodeId = null,
            accessibilityLabel = "표시할 경로 지도가 없습니다.",
        )
    }
}

data class MiniMapNodeUiModel(
    val id: String,
    val label: String,
    val row: Int,
    val column: Int,
    val type: MiniMapNodeType = MiniMapNodeType.WAYPOINT,
) {
    val isCurrent: Boolean
        get() = type == MiniMapNodeType.CURRENT

    val isDestination: Boolean
        get() = type == MiniMapNodeType.DESTINATION

    fun toRouteNodeUiModel(
        currentNodeId: String?,
        destinationNodeId: String?,
    ): RouteNodeUiModel = RouteNodeUiModel(
        row = row,
        column = column,
        label = label,
        state = when (id) {
            currentNodeId, destinationNodeId -> RouteNodeUiModel.State.HIGHLIGHTED
            else -> RouteNodeUiModel.State.PASSED
        },
    )
}

data class MiniMapEdgeUiModel(
    val id: String,
    val fromNodeId: String,
    val toNodeId: String,
    val isHighlighted: Boolean = false,
) {
    companion object {
        fun edgeId(fromNodeId: String, toNodeId: String): String = "$fromNodeId-$toNodeId"
    }
}

enum class MiniMapNodeType {
    CURRENT,
    WAYPOINT,
    DESTINATION,
}
