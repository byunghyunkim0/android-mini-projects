package com.woowa.nureongi.ui.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class GuidanceUiStateTest {
    @Test
    fun currentStepIsDerivedFromIndex() {
        val state = NureongiUiFixtures.Guidance.copy(currentStepIndex = 1)

        assertEquals(2, state.displayStepNumber)
        assertEquals("step-02", state.currentStep?.id)
        assertFalse(state.isLastStep)
    }

    @Test
    fun guidanceStepMatchesDirectionGuideCardParameters() {
        val step = NureongiUiFixtures.Guidance.currentStep

        assertEquals("8m 직진", step?.instructionText)
        assertEquals("다음 점형 블록 · 출구 갈림길", step?.landmarkText)
        assertEquals("앞으로 8미터 직진하세요. 다음 점형 블록에서 멈춰 주세요.", step?.guideMessage)
    }

    @Test
    fun miniMapMatchesRouteMapCardParameters() {
        val map = NureongiUiFixtures.MiniMap

        assertEquals(5, map.rows)
        assertEquals(4, map.columns)
        assertEquals(3, map.path.size)
        assertEquals(RouteNodeUiModel.State.HIGHLIGHTED, map.path.first().state)
        assertEquals(RouteNodeUiModel.State.PASSED, map.path[1].state)
        assertEquals(RouteNodeUiModel.State.HIGHLIGHTED, map.path.last().state)
    }
}
