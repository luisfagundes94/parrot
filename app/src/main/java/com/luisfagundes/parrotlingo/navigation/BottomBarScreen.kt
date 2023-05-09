package com.luisfagundes.parrotlingo.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector
import com.luisfagundes.commons_util.RouteParams
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.parrotlingo.R

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val screenNameId: Int
) {
    object Translation : BottomBarScreen(
        route = "translation/{$LANG_ID}/{$IS_SOURCE_LANGUAGE}",
        icon = Icons.Filled.Translate,
        screenNameId = R.string.translation
    ) {
        fun addLanguage(
            id: String,
            isSourceLanguage: Boolean,
        ) = "translation/$id/$isSourceLanguage"
    }

    object Saved : BottomBarScreen(
        route = "saved",
        icon = Icons.Filled.Bookmark,
        screenNameId = R.string.saved
    )

    object Settings : BottomBarScreen(
        route = "settings",
        icon = Icons.Filled.Settings,
        screenNameId = R.string.settings
    )

    object LanguageList : BottomBarScreen(
        route = "languageList",
        icon = Icons.Filled.Language,
        screenNameId = R.string.language_list
    )

    companion object {
        const val LANG_ID = "langId"
    }
}

