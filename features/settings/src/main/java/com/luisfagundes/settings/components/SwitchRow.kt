package com.luisfagundes.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.settings.R

@Composable
fun SwitchRow(
    modifier: Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(stringResource(R.string.night_mode))
        Spacer(modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
