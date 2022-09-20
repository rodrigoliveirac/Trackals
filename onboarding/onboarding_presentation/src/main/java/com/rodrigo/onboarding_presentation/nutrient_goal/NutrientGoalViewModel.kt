package com.rodrigo.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigo.core.R
import com.rodrigo.core.domain.preferences.Preferences
import com.rodrigo.core.domain.use_case.FilterOutDigits
import com.rodrigo.core.navigation.Route
import com.rodrigo.core.util.UiEvent
import com.rodrigo.core.util.UiText
import com.rodrigo.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(carbsRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(proteinRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(fatRatio = filterOutDigits(event.ratio))
            }

            is NutrientGoalEvent.OnNextClick -> {
                viewModelScope.launch {
                    when (val result =
                        validateNutrients(state.carbsRatio, state.proteinRatio, state.fatRatio)) {
                        is ValidateNutrients.Result.Success -> {
                            preferences.saveCarbRatio(result.carbsRatio)
                            preferences.saveProteinRatio(result.proteinRatio)
                            preferences.saveFatRatio(result.fatRatio)
                            viewModelScope.launch {
                                _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                            }
                        }
                        is ValidateNutrients.Result.Error -> {
                            viewModelScope.launch {
                                _uiEvent.send(
                                    UiEvent.ShowSnackbar(
                                        result.message
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}