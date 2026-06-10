package com.woowa.nureongi.ui.navigation

import androidx.navigation.NavController

internal fun NavController.navigateToCurrentLocationSelection() {
    navigate(CurrentLocationDestination) {
        launchSingleTop = true
    }
}

internal fun NavController.navigateToGuidance(destination: GuidanceDestination) {
    navigate(destination) {
        launchSingleTop = true
    }
}

internal fun NavController.finishGuidance(): Boolean {
    return popBackStack<DestinationSelectionDestination>(inclusive = false)
}
