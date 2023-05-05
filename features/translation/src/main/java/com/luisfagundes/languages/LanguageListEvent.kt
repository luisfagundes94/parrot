package com.luisfagundes.languages

sealed class LanguageListEvent {
    object GetCountryList : LanguageListEvent()
}
