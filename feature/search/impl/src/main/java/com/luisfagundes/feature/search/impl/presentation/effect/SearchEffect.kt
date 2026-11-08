package com.luisfagundes.feature.search.impl.presentation.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface SearchEffect : UiEffect {
    data class NavigateToWordDetail(val word: String) : SearchEffect
}
