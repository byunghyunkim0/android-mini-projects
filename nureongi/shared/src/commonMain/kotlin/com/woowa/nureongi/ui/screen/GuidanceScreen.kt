package com.woowa.nureongi.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.woowa.nureongi.ui.component.CtaButton
import com.woowa.nureongi.ui.component.DirectionGuideCard
import com.woowa.nureongi.ui.component.NavigationTopBar
import com.woowa.nureongi.ui.component.SegmentedProgressIndicator
import com.woowa.nureongi.ui.component.StatTile
import com.woowa.nureongi.ui.component.StraightArrowIcon
import com.woowa.nureongi.ui.component.TactileMiniMap
import com.woowa.nureongi.ui.component.VoiceGuideButton
import com.woowa.nureongi.ui.guidance.GuidanceViewModel
import com.woowa.nureongi.ui.model.GuidanceStepUiModel
import com.woowa.nureongi.ui.model.GuidanceUiState
import com.woowa.nureongi.ui.model.PreviewGuidanceUiState
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme

@Composable
fun GuidanceRoute(
    initialState: GuidanceUiState,
    onCloseGuidance: () -> Unit,
    onVoiceGuideClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GuidanceViewModel = viewModel {
        GuidanceViewModel(initialState = initialState)
    },
) {
    GuidanceScreen(
        state = viewModel.uiState,
        onNextStepClick = viewModel::onNextStep,
        onCloseClick = onCloseGuidance,
        onVoiceGuideClick = onVoiceGuideClick,
        modifier = modifier,
    )
}

@Composable
fun GuidanceScreen(
    state: GuidanceUiState,
    onNextStepClick: () -> Unit,
    onCloseClick: () -> Unit,
    onVoiceGuideClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NureongiColors.Background)
            .safeContentPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            GuidanceHeader(
                destinationName = state.destinationName,
                currentStep = state.currentStep,
                totalSteps = state.totalSteps,
                onCloseClick = onCloseClick,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                CurrentGuidanceCard(
                    guidance = state.currentGuidance,
                    isArrived = state.isArrived,
                )
                GuidanceSummaryCards(
                    remainingDistanceText = state.remainingDistanceText,
                    remainingTactileBlockText = state.remainingTactileBlockText,
                )
                TactileMiniMap(uiModel = state.miniMap)
            }
            GuidanceBottomActions(
                nextButtonText = state.nextButtonText,
                onVoiceGuideClick = onVoiceGuideClick,
                onNextStepClick = if (state.isArrived) onCloseClick else onNextStepClick,
            )
        }
    }
}

@Composable
fun GuidanceHeader(
    destinationName: String,
    currentStep: Int,
    totalSteps: Int,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationTopBar(
        destinationName = destinationName,
        currentStep = currentStep,
        totalSteps = totalSteps,
        onCloseClick = onCloseClick,
        modifier = modifier,
    )
}

@Composable
fun StepProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    SegmentedProgressIndicator(
        totalSteps = totalSteps,
        completedSteps = currentStep,
        modifier = modifier,
    )
}

@Composable
fun CurrentGuidanceCard(
    guidance: GuidanceStepUiModel,
    isArrived: Boolean,
    modifier: Modifier = Modifier,
) {
    DirectionGuideCard(
        instruction = guidance.instruction,
        landmark = guidance.landmark,
        guideMessage = guidance.guideMessage,
        modifier = modifier.semantics {
            liveRegion = if (isArrived) {
                LiveRegionMode.Assertive
            } else {
                LiveRegionMode.Polite
            }
        },
        leadingIcon = {
            StraightArrowIcon(
                modifier = Modifier.size(64.dp)
            )
        },
    )
}

@Composable
fun GuidanceSummaryCards(
    remainingDistanceText: String,
    remainingTactileBlockText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatTile(
            value = remainingDistanceText,
            label = "남은 거리",
            modifier = Modifier.weight(1f),
        )
        StatTile(
            value = remainingTactileBlockText,
            label = "남은 점형 블록",
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun NextStepButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CtaButton(
        text = text,
        onClick = onClick,
        containerColor = NureongiColors.Accent,
        contentColor = NureongiColors.OnAccent,
        modifier = modifier
            .height(72.dp)
            .semantics {
                contentDescription = "$text 버튼"
            },
    )
}

@Composable
fun GuidanceBottomActions(
    nextButtonText: String,
    onVoiceGuideClick: () -> Unit,
    onNextStepClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        VoiceGuideButton(
            onClick = onVoiceGuideClick,
            modifier = Modifier
                .size(72.dp)
                .semantics {
                    contentDescription = "현재 안내 음성으로 다시 듣기 버튼"
                },
        )
        NextStepButton(
            text = nextButtonText,
            onClick = onNextStepClick,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview
@Composable
private fun GuidanceScreenPreview() {
    NureongiTheme {
        GuidanceScreen(
            state = PreviewGuidanceUiState,
            onNextStepClick = {},
            onCloseClick = {},
            onVoiceGuideClick = {},
        )
    }
}

@Preview
@Composable
private fun ArrivedGuidanceScreenPreview() {
    NureongiTheme {
        GuidanceScreen(
            state = PreviewGuidanceUiState.copy(
                currentStepIndex = PreviewGuidanceUiState.steps.lastIndex,
                isArrived = true,
            ),
            onNextStepClick = {},
            onCloseClick = {},
            onVoiceGuideClick = {},
        )
    }
}
