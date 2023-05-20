package com.luisfagundes.parrotlingo.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.luisfagundes.provider.LanguageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {

    @Inject lateinit var languageProvider: LanguageProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            setCurrentLocale()
            MainRoot()
        }
    }

    @Composable
    private fun setCurrentLocale() {
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            val languageCode = languageProvider.getLanguageCode()
            languageProvider.setLocale(
                language = languageCode,
                context = context,
            )
        }
    }
}
