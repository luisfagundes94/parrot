package com.luisfagundes.parrot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.core.navigation.Navigator
import com.luisfagundes.core.navigation.rememberNavigationState

@Composable
internal fun AppNavigation(
    installers: Set<EntryProviderInstaller>
) {
    val destinations = TopLevelDestinations.entries.toSet()
    val state = rememberNavigationState(destinations.map { it.key })
    val navigator = remember(state) { Navigator(state) }
    val entryProvider = entryProvider {
        installers.forEach { install -> install(navigator) }
    }
    val decorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator<NavKey>(),
    )
    val entries = state.stacksInUse.flatMap { key ->
        rememberDecoratedNavEntries(state.backStacks.getValue(key), decorators, entryProvider)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { dest ->
                    val label = stringResource(dest.labelResId)

                    NavigationBarItem(
                        selected = dest.key == state.currentKey,
                        onClick = { navigator.navigate(dest.key) },
                        icon = { Icon(dest.icon, contentDescription = label) },
                        label = { Text(label) },
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            entries = entries,
            onBack = navigator::goBack,
            modifier = Modifier.padding(padding),
        )
    }
}