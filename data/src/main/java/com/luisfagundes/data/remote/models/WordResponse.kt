package com.luisfagundes.data.remote.models

data class WordResponse(
  val audio_links: List<AudioLinkResponse>,
  val featured: Boolean,
  val grammar_info: Any? = null,
  val pos: String,
  val text: String,
  val translations: List<TranslationResponse>,
)
