package com.luisfagundes.translation.components

import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.translation.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    var shouldOpenDeniedDialog by remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(key1 = permissionState.status) {
        when (val status = permissionState.status) {
            is PermissionStatus.Granted -> {
                doNothing()
            }

            is PermissionStatus.Denied -> {
                if (status.shouldShowRationale) shouldOpenDeniedDialog = true
                else permissionState.launchPermissionRequest()
            }
        }
    }

    if (shouldOpenDeniedDialog) {
        PermissionDeniedDialog {
            shouldOpenDeniedDialog = it
        }
    }
}

@Composable
private fun PermissionDeniedDialog(
    shouldOpenDialog: (Boolean) -> Unit
) {
    val textToShow = stringResource(R.string.notification_request_text)

    AlertDialog(
        onDismissRequest = { shouldOpenDialog(false) },
        confirmButton = {
            TextButton(
                onClick = { shouldOpenDialog(false) }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { shouldOpenDialog(false) }
            ) {
                Text("Dismiss")
            }
        },
        title = { Text("Notification Permission") },
        text = { Text(textToShow) },
    )
}
