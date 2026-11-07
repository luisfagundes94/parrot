package com.luisfagundes.parrot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.parrot.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var installers: @JvmSuppressWildcards Set<EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParrotTheme { AppNavigation(installers) }
        }
    }
}