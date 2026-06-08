package com.woowa.nureongi.ui.model

/**
 * [com.woowa.nureongi.ui.component.RouteMapCard] 의 점-선 경로 다이어그램에 표시되는 노드.
 * 위치 정보는 가로(row) · 세로(column) 인덱스만 가지며, 경로 계산·도메인 모델과는 분리한다.
 */
data class RouteNodeUiModel(
    val row: Int,
    val column: Int,
    val label: String? = null,
    val state: State = State.NEUTRAL,
) {
    enum class State {
        /** 경로에 포함되지 않은 일반 노드 */
        NEUTRAL,

        /** 지나온 경로에 포함된 노드 */
        PASSED,

        /** 현재 위치 또는 목적지 등 강조해야 하는 노드 */
        HIGHLIGHTED,
    }
}
