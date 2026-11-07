package com.luisfagundes.core.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(val state: NavigationState) {
    fun navigate(key: NavKey) {
        if (key in state.topLevelKeys) state.currentKey = key
        else state.currentStack.add(key)
    }

    fun goBack() {
        val stack = state.currentStack
        when {
            stack.size > 1 -> stack.removeLastOrNull()
            state.currentKey != state.startKey -> state.currentKey = state.startKey
        }
    }
}