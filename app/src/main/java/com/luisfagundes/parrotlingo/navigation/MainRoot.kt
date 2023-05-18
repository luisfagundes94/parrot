package com.luisfagundes.parrotlingo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.luisfagundes.data.local.services.PushNotificationManagerImpl
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.parrotlingo.R
import com.luisfagundes.parrotlingo.navigation.graphs.RootNavGraph
import com.luisfagundes.provider.shouldUseDarkMode
import com.luisfagundes.theme.ParrotTheme

@Composable
fun MainRoot(
    viewModel: NavigationViewModel = hiltViewModel(),
) {
    val isDarkMode = viewModel.getThemeProvider().shouldUseDarkMode()

    viewModel.onEvent(
        NavigationEvent.CreateNotificationChannel(
            context = LocalContext.current,
            notificationChannelInfo = createNotificationChannelInfo(),
        ),
    )
    ParrotTheme(isDarkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            RootNavGraph(
                navHostController = rememberNavController(),
            )
        }
    }
}

@Composable
private fun createNotificationChannelInfo() = NotificationChannelInfo(
    id = PushNotificationManagerImpl.CHANNEL_ID,
    name = stringResource(id = R.string.channel_name),
    description = stringResource(id = R.string.channel_description),
)
