package com.rodrigo.tracker_presentation.tracker_overview.components

import android.media.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.sp
import com.rodrigo.core.R.string
import com.rodrigo.core_ui.LocalSpacing
import com.rodrigo.tracker_presentation.components.NutrientsInfo
import com.rodrigo.tracker_presentation.components.UnitDisplay
import com.rodrigo.tracker_presentation.tracker_overview.Meal
import com.rodrigo.tracker_presentation.tracker_overview.TrackerOverviewEvent

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3,
                    )
                    Icon(
                        imageVector = if (meal.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (meal.isExpanded) stringResource(id = string.collapse) else stringResource(
                            id = string.extend
                        )
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = string.kcal),
                        amountTextSize = 30.sp
                    )
                    Row {
                        NutrientsInfo(
                            name = stringResource(id = string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = string.grams)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientsInfo(
                            name = stringResource(id = string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = string.protein)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientsInfo(
                            name = stringResource(id = string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = string.fat)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}