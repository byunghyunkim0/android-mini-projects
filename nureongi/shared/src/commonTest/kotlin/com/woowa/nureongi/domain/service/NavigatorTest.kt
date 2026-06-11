package com.woowa.nureongi.domain.service

import com.woowa.nureongi.domain.data.PangyoStationMapData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NavigatorTest {
    private val station = PangyoStationMapData.getMapData().station
    private val navigator = Navigator()

    @Test
    fun `최단 거리 경로를 RouteStep 목록으로 반환한다`() {
        val start = station.findNavigationPoint("exit-1")!!
        val destination = station.findNavigationPoint("service-center")!!

        val route = navigator.findRoute(station, start, destination)

        assertEquals(listOf("concourse-junction", "service-center"), route.steps.map { it.toNode.id })
        assertEquals(24f, route.totalDistance)
        assertEquals(listOf(24f, 8f), route.steps.map { it.remainingDistance })
        assertEquals(135, route.startPoint.initialAngle)
    }

    @Test
    fun `stepIndex로 경로 진행 상태를 계산한다`() {
        val start = station.findNavigationPoint("exit-1")!!
        val destination = station.findNavigationPoint("service-center")!!
        val route = navigator.findRoute(station, start, destination)

        assertFalse(route.isArrived(0))
        assertEquals("concourse-junction", route.currentStep(0)?.toNode?.id)
        assertEquals(24f, route.remainingDistance(0))
        assertEquals(1, route.advance(0))

        val arrivedIndex = route.steps.size
        assertTrue(route.isArrived(arrivedIndex))
        assertNull(route.currentStep(arrivedIndex))
        assertEquals(0f, route.remainingDistance(arrivedIndex))
        assertEquals(arrivedIndex, route.advance(arrivedIndex))
    }
}
