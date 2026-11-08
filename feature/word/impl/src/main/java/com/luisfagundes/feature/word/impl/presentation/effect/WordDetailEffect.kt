package com.luisfagundes.feature.word.impl.presentation.effect

import com.luisfagundes.core.common.presentation.arch.effect.UiEffect

internal sealed interface WordDetailEffect : UiEffect {
    data object NavigateBack : WordDetailEffect
}
