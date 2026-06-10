package com.woowa.nureongi.ui.model

internal object PangyoStationUiData {
    val currentLocations = listOf(
        CurrentLocationItemUiModel("exit-1", PlaceUiModel("1번 출구", "지상 · 버스정류장 방면")),
        CurrentLocationItemUiModel("exit-2", PlaceUiModel("2번 출구", "지상 · 광장 방면")),
        CurrentLocationItemUiModel("gate", PlaceUiModel("개찰구", "대합실 입구")),
        CurrentLocationItemUiModel("restroom", PlaceUiModel("화장실", "대합실 왼쪽")),
        CurrentLocationItemUiModel("service-center", PlaceUiModel("고객센터", "대합실 오른쪽")),
        CurrentLocationItemUiModel("stairs", PlaceUiModel("계단", "승강장 방면 계단")),
        CurrentLocationItemUiModel("elevator", PlaceUiModel("엘리베이터", "휠체어 · 유모차")),
    )

    val destinations = listOf(
        DestinationItemUiModel("exit-1", PlaceUiModel("1번 출구", "지상 · 버스정류장 방면")),
        DestinationItemUiModel("exit-2", PlaceUiModel("2번 출구", "지상 · 광장 방면")),
        DestinationItemUiModel("restroom", PlaceUiModel("화장실", "대합실 왼쪽")),
        DestinationItemUiModel("service-center", PlaceUiModel("고객센터", "대합실 오른쪽")),
        DestinationItemUiModel("stairs", PlaceUiModel("계단", "승강장 방면 계단")),
    )
}
