package com.rodrigo.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rodrigo.core.util.UiEvent
import com.rodrigo.core_ui.LocalSpacing
import com.rodrigo.tracker_presentation.R
import com.rodrigo.tracker_presentation.components.DaySelector
import com.rodrigo.tracker_presentation.tracker_overview.TrackerOverviewEvent.*
import com.rodrigo.tracker_presentation.tracker_overview.components.AddButton
import com.rodrigo.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.rodrigo.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.rodrigo.tracker_presentation.tracker_overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = { viewModel.onEvent(OnPreviousDayClick) },
                onNextDayClick = { viewModel.onEvent(OnNextDayClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = { viewModel.onEvent(OnToggleMealClick(meal)) },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        state.trackedFoods.forEach { food ->
                            TrackedFoodItem(
                                trackedFood = food,
                                onDeleteClick = {
                                    viewModel.onEvent(
                                        OnDeleteTrackedFoodClick(food)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                        AddButton(
                            text = stringResource(
                                id = com.rodrigo.core.R.string.add,
                                meal.name.asString(context)
                            ),
                            onClick = { viewModel.onEvent(OnAddFoodClick(meal)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}