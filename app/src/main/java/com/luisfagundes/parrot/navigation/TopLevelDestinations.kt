package com.luisfagundes.parrot.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.luisfagundes.feature.review.api.navigation.ReviewNavKey
import com.luisfagundes.feature.review.impl.presentation.utility.ReviewIcon
import com.luisfagundes.feature.saved.api.navigation.SavedNavKey
import com.luisfagundes.feature.saved.impl.presentation.utility.SavedIcon
import com.luisfagundes.feature.search.api.navigation.SearchNavKey
import com.luisfagundes.feature.search.impl.presentation.utility.SearchIcon
import com.luisfagundes.feature.review.impl.R as ReviewResources
import com.luisfagundes.feature.saved.impl.R as SavedResources
import com.luisfagundes.feature.search.impl.R as SearchResources

enum class TopLevelDestinations(
    val key: NavKey,
    val icon: ImageVector,
    @StringRes val labelResId: Int
) {
    SEARCH(
        key = SearchNavKey,
        labelResId = SearchResources.string.search_title,
        icon = SearchIcon
    ),
    SAVED(
        key = SavedNavKey,
        labelResId = SavedResources.string.saved_title,
        icon = SavedIcon
    ),
    REVIEW(
        key = ReviewNavKey,
        labelResId = ReviewResources.string.review_title,
        icon = ReviewIcon
    ),
}