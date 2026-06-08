package com.woowa.nureongi.ui.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DestinationSelectionUiStateTest {
    @Test
    fun selectedDestinationDrivesCtaState() {
        val state = NureongiUiFixtures.DestinationSelection

        assertEquals("exit-01", state.selectedDestinationId)
        assertTrue(state.canStartGuidance)
        assertEquals("1번 출구까지 안내 시작", state.startGuidanceButtonText)
    }

    @Test
    fun destinationItemCanBePassedToPr01PlaceListItem() {
        val selected = NureongiUiFixtures.DestinationSelection.selectedDestination

        assertEquals("1번 출구", selected?.place?.name)
        assertEquals("지상 버스정류장 방면", selected?.place?.location)
    }

    @Test
    fun selectDestinationMarksOnlyRequestedItem() {
        val state = NureongiUiFixtures.DestinationSelection.selectDestination("exit-02")

        assertEquals("exit-02", state.selectedDestinationId)
        assertTrue(state.destinations.single { it.id == "exit-02" }.isSelected)
        assertFalse(state.destinations.single { it.id == "exit-01" }.isSelected)
    }
}
