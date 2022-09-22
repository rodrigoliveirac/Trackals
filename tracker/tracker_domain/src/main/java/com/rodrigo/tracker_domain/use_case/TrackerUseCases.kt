package com.rodrigo.tracker_domain.use_case

data class TrackerUseCases(
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getFoodsForDate: GetFoodsForDate,
    val deleteTrackerFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients
)
