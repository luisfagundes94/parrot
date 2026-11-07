package com.luisfagundes.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

typealias EntryProviderInstaller = EntryProviderScope<NavKey>.(Navigator) -> Unit

class NavigationState(
    val topLevelKeys: List<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>,
    private val currentIndex: MutableIntState
) {
    val startKey: NavKey
        get() = topLevelKeys.first()

    var currentKey: NavKey
        get() = topLevelKeys[currentIndex.intValue]
        set(value) {
            currentIndex.intValue = topLevelKeys.indexOf(value).coerceAtLeast(0)
        }

    val currentStack: NavBackStack<NavKey>
        get() = backStacks.getValue(currentKey)

    val stacksInUse: List<NavKey>
        get() = if (currentKey == startKey) listOf(startKey) else listOf(startKey, currentKey)
}

@Composable
fun rememberNavigationState(topLevelKeys: List<NavKey>): NavigationState {
    val index = rememberSaveable { mutableIntStateOf(0) }
    val stacks = topLevelKeys.associateWith { rememberNavBackStack(it) }
    return remember(topLevelKeys, stacks) { NavigationState(topLevelKeys, stacks, index) }
}