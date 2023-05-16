package com.luisfagundes.languages

sealed class LanguageListEvent {
  object GetLanguageList : LanguageListEvent()
  object OnBackPressed : LanguageListEvent()

  data class OnSearchTextChanged(val text: String) : LanguageListEvent()
  data class OnLanguageClicked(
    val id: String,
    val isSourceLanguage: Boolean,
  ) : LanguageListEvent()
}
