package com.rodrigo.trackals.navigation

import androidx.navigation.NavController
import com.rodrigo.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}