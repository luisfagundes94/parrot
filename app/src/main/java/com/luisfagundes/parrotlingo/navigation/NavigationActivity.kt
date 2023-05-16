package com.luisfagundes.parrotlingo.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.luisfagundes.data.local.services.AlarmReceiver
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.parrotlingo.R
import com.luisfagundes.parrotlingo.navigation.graphs.RootNavGraph
import com.luisfagundes.theme.ParrotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AlarmReceiver.createNotificationChannel(
            notificationChannelInfo = NotificationChannelInfo(
                id = "1",
                name = getString(R.string.channel_name),
                description = getString(R.string.channel_description),
            ),
            context = this,
        )

        setContent {
            ParrotTheme {
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
    }
}
