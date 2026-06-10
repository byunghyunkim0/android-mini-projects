package com.woowa.nureongi.ui.location

import com.woowa.nureongi.ui.model.CurrentLocationItemUiModel
import com.woowa.nureongi.ui.model.CurrentLocationSelectionUiState
import com.woowa.nureongi.ui.model.PlaceUiModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CurrentLocationSelectionViewModelTest {
    private val locations = listOf(
        CurrentLocationItemUiModel(
            id = "gate",
            place = PlaceUiModel("개찰구", "대합실 입구"),
        ),
        CurrentLocationItemUiModel(
            id = "exit-2",
            place = PlaceUiModel("2번 출구", "지상 · 광장 방면"),
        ),
    )

    @Test
    fun `위치 ID를 선택하면 선택 상태와 결과가 변경된다`() {
        val viewModel = CurrentLocationSelectionViewModel(
            initialState = CurrentLocationSelectionUiState(locations = locations),
        )

        viewModel.onLocationSelected("exit-2")

        assertEquals("exit-2", viewModel.uiState.value.selectedLocationId)
        assertEquals("exit-2", viewModel.locationIdForResult())
    }

    @Test
    fun `존재하지 않는 위치 ID를 선택하면 기존 선택을 유지한다`() {
        val viewModel = CurrentLocationSelectionViewModel(
            initialState = CurrentLocationSelectionUiState(
                locations = locations,
                selectedLocationId = "gate",
            ),
        )

        viewModel.onLocationSelected("unknown")

        assertEquals("gate", viewModel.uiState.value.selectedLocationId)
    }

    @Test
    fun `초기 선택 위치가 목록에 없으면 선택을 해제한다`() {
        val viewModel = CurrentLocationSelectionViewModel(
            initialState = CurrentLocationSelectionUiState(
                locations = locations,
                selectedLocationId = "unknown",
            ),
        )

        assertNull(viewModel.uiState.value.selectedLocationId)
        assertNull(viewModel.locationIdForResult())
    }

    @Test
    fun `로딩 중에는 위치 선택을 무시하고 결과를 반환하지 않는다`() {
        val viewModel = CurrentLocationSelectionViewModel(
            initialState = CurrentLocationSelectionUiState(
                locations = locations,
                selectedLocationId = "gate",
                isLoading = true,
            ),
        )

        viewModel.onLocationSelected("exit-2")

        assertEquals("gate", viewModel.uiState.value.selectedLocationId)
        assertNull(viewModel.locationIdForResult())
    }
}
