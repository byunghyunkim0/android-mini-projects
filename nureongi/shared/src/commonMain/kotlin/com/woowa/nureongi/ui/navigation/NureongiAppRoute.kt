package com.woowa.nureongi.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.woowa.nureongi.ui.screen.CurrentLocationSelectionScreen
import com.woowa.nureongi.ui.screen.DestinationSelectionScreen
import com.woowa.nureongi.ui.screen.GuidanceScreen
import com.woowa.nureongi.ui.theme.NureongiColors

@Composable
internal fun NureongiAppRoute(
    modifier: Modifier = Modifier,
    viewModel: NureongiAppViewModel = viewModel { NureongiAppViewModel() },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationSelectionDestination,
        modifier = modifier
            .fillMaxSize()
            .background(NureongiColors.Background),
    ) {
        composable<CurrentLocationDestination> {
            CurrentLocationSelectionScreen(
                state = uiState.currentLocationState,
                onBackClick = navController::popBackStack,
                onLocationSelected = { locationId ->
                    viewModel.onLocationSelected(locationId)?.let {
                        navController.popBackStack<DestinationSelectionDestination>(
                            inclusive = false,
                        )
                    }
                },
            )
        }
        composable<DestinationSelectionDestination> {
            DestinationSelectionScreen(
                state = uiState.destinationState,
                onDestinationSelected = viewModel::onDestinationSelected,
                onChangeLocationClick = navController::navigateToCurrentLocationSelection,
                onStartGuidance = {
                    viewModel.onStartGuidance()?.let { request ->
                        navController.navigateToGuidance(request)
                    }
                },
            )
        }
        composable<GuidanceDestination> { backStackEntry ->
            val destination: GuidanceDestination = backStackEntry.toRoute()
            LaunchedEffect(destination) {
                viewModel.onGuidanceDestinationEntered(
                    currentLocationId = destination.currentLocationId,
                    destinationId = destination.destinationId,
                )
            }

            val guidanceState = uiState.guidanceState
            if (guidanceState == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(NureongiColors.Background),
                )
            } else {
                GuidanceScreen(
                    state = guidanceState,
                    onNextStepClick = viewModel::onNextGuidanceStep,
                    onCloseClick = {
                        navController.finishGuidance()
                    },
                    onVoiceGuideClick = {},
                )
            }
        }
    }
}
