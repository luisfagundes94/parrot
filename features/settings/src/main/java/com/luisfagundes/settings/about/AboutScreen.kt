package com.luisfagundes.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.luisfagundes.commonsUi.ParrotTopBar
import com.luisfagundes.settings.PARROT_GITHUB_URL
import com.luisfagundes.settings.R
import com.luisfagundes.theme.spacing

@Composable
fun AboutScreen() {
    ParrotTopBar(
        name = stringResource(id = R.string.about),
        onBackPressed = { /*TODO*/ },
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.default),
            elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.elevatedCardColors(),
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.default),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.luis_felipe_carvalho_fagundes),
                    textAlign = TextAlign.Start,
                )
                Spacer(Modifier.height(MaterialTheme.spacing.small))
                Text(
                    text = PARROT_GITHUB_URL,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}
