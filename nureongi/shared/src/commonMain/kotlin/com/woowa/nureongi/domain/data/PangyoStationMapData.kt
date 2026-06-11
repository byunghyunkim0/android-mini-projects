package com.woowa.nureongi.domain.data

import com.woowa.nureongi.domain.model.Edge
import com.woowa.nureongi.domain.model.NavigationPoint
import com.woowa.nureongi.domain.model.Node
import com.woowa.nureongi.domain.model.Station

object PangyoStationMapData : StationMapDataSource {
    private val data = createMapData()

    override fun getMapData(): StationMapData = data

    private fun createMapData(): StationMapData {
        val exit1 = Node(
            id = "exit-1",
            name = "작은 강의실",
            landmark = "버스정류장 방면",
            floor = 1,
        )
        val exit2 = Node(
            id = "exit-2",
            name = "작은 강의실",
            landmark = "광장 방면",
            floor = 1,
        )
        val gate = Node(
            id = "gate",
            name = "개찰구",
            landmark = "대합실 입구",
            floor = -1,
        )
        val restroom = Node(
            id = "restroom",
            name = "화장실",
            landmark = "대합실 왼쪽",
            floor = -1,
        )
        val serviceCenter = Node(
            id = "service-center",
            name = "고객센터",
            landmark = "대합실 오른쪽",
            floor = -1,
        )
        val stairs = Node(
            id = "stairs",
            name = "계단",
            landmark = "승강장 방면 계단",
            floor = -1,
        )
        val elevator = Node(
            id = "elevator",
            name = "엘리베이터",
            landmark = "휠체어·유모차 이용 가능",
            floor = -1,
        )
        val concourseJunction = Node(
            id = "concourse-junction",
            name = "대합실 중앙 갈림길",
            landmark = "점형 블록 갈림길",
            floor = -1,
        )

        val nodes = listOf(
            exit1,
            exit2,
            gate,
            restroom,
            serviceCenter,
            stairs,
            elevator,
            concourseJunction,
        )
        val edges = buildList {
            addBidirectionalEdges("junction-exit-1", concourseJunction, exit1, distance = 16f, angle = 315)
            addBidirectionalEdges("junction-exit-2", concourseJunction, exit2, distance = 16f, angle = 45)
            addBidirectionalEdges("junction-gate", concourseJunction, gate, distance = 4f, angle = 180)
            addBidirectionalEdges("junction-restroom", concourseJunction, restroom, distance = 8f, angle = 270)
            addBidirectionalEdges(
                "junction-service-center",
                concourseJunction,
                serviceCenter,
                distance = 8f,
                angle = 90,
            )
            addBidirectionalEdges("junction-stairs", concourseJunction, stairs, distance = 14f, angle = 225)
            addBidirectionalEdges("junction-elevator", concourseJunction, elevator, distance = 14f, angle = 135)
        }
        val navigationPoints = listOf(
            NavigationPoint(nodeId = exit1.id, initialAngle = 135),
            NavigationPoint(nodeId = exit2.id, initialAngle = 225),
            NavigationPoint(nodeId = gate.id, initialAngle = 0),
            NavigationPoint(nodeId = restroom.id, initialAngle = 90),
            NavigationPoint(nodeId = serviceCenter.id, initialAngle = 270),
            NavigationPoint(nodeId = stairs.id, initialAngle = 45),
            NavigationPoint(nodeId = elevator.id, initialAngle = 315),
        )

        val station = Station(
            id = "pangyo-station",
            name = "판교역",
            nodes = nodes,
            edges = edges,
            navigationPoints = navigationPoints,
        )

        return StationMapData(
            station = station,
            rows = 10,
            columns = 10,
            nodePositions = mapOf(
                exit1.id to MapNodePosition(row = 0, column = 0),
                exit2.id to MapNodePosition(row = 0, column = 2),
                concourseJunction.id to MapNodePosition(row = 1, column = 1),
                gate.id to MapNodePosition(row = 2, column = 1),
                restroom.id to MapNodePosition(row = 2, column = 0),
                serviceCenter.id to MapNodePosition(row = 2, column = 2),
                stairs.id to MapNodePosition(row = 4, column = 0),
                elevator.id to MapNodePosition(row = 4, column = 2),
            ),
        )
    }
}

private fun MutableList<Edge>.addBidirectionalEdges(
    id: String,
    first: Node,
    second: Node,
    distance: Float,
    angle: Int,
) {
    add(
        Edge(
            id = "$id-forward",
            from = first,
            to = second,
            distance = distance,
            angle = angle,
        ),
    )
    add(
        Edge(
            id = "$id-reverse",
            from = second,
            to = first,
            distance = distance,
            angle = (angle + 180) % 360,
        ),
    )
}
