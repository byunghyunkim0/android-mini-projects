package com.woowa.nureongi.ui.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DestinationSelectionUiStateTest {
    @Test
    fun selectedDestinationIsFoundById() {
        val state = NureongiUiFixtures.DestinationSelection

        assertEquals("exit-01", state.selectedDestination?.id)
        assertTrue(state.canStartGuidance)
    }

    @Test
    fun guidanceCannotStartWithoutCurrentLocation() {
        val state = NureongiUiFixtures.DestinationSelection.copy(currentLocation = null)

        assertFalse(state.canStartGuidance)
    }
}
