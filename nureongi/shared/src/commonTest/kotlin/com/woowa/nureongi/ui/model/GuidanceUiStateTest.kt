package com.woowa.nureongi.ui.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GuidanceUiStateTest {
    @Test
    fun currentStepAndProgressAreDerivedFromIndex() {
        val state = NureongiUiFixtures.Guidance.copy(currentStepIndex = 1)

        assertEquals(2, state.displayStepNumber)
        assertEquals(2, state.completedStepCount)
        assertEquals("step-02", state.currentStep?.id)
        assertEquals(2f / 3f, state.progressRatio)
        assertFalse(state.isLastStep)
    }

    @Test
    fun currentStepCanBePassedToPr01DirectionGuideCard() {
        val step = NureongiUiFixtures.Guidance.currentStep

        assertEquals("8m 직진", step?.instructionText)
        assertEquals("다음 점형 블록 · 출구 갈림길", step?.landmarkText)
        assertEquals("앞으로 8미터 직진하세요. 다음 점형 블록에서 멈춰 주세요.", step?.guideMessage)
    }

    @Test
    fun miniMapCanBePassedToPr01RouteMapCard() {
        val map = NureongiUiFixtures.MiniMap

        assertEquals(5, map.rows)
        assertEquals(4, map.columns)
        assertEquals(3, map.routePath.size)
        assertEquals(RouteNodeUiModel.State.HIGHLIGHTED, map.routePath.first().state)
        assertEquals(RouteNodeUiModel.State.PASSED, map.routePath[1].state)
        assertEquals(RouteNodeUiModel.State.HIGHLIGHTED, map.routePath.last().state)
    }

    @Test
    fun movingAfterLastStepMarksArrived() {
        val lastStepState = NureongiUiFixtures.Guidance.copy(currentStepIndex = 2)
        val arrivedState = lastStepState.moveToNextStep()

        assertTrue(arrivedState.isArrived)
        assertEquals(1f, arrivedState.progressRatio)
        assertEquals(3, arrivedState.completedStepCount)
        assertEquals("안내 종료", arrivedState.primaryButtonText)
        assertEquals("0m", arrivedState.remainingDistanceText)
    }
}
