package com.woowa.nureongi.ui.model

/**
 * [com.woowa.nureongi.ui.component.PlaceListItem] 등 장소/목적지 목록에 쓰이는 UI 전용 모델.
 * 도메인의 장소·점형 블럭 모델을 그대로 넘기지 않고, 화면에 표시할 값만 모아서 전달한다.
 */
data class PlaceUiModel(
    val name: String,
    val location: String,
)
