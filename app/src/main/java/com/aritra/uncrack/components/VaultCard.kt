package com.aritra.uncrack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aritra.uncrack.domain.model.Account
import com.aritra.uncrack.ui.theme.SurfaceTintLight
import com.aritra.uncrack.ui.theme.medium18
import com.aritra.uncrack.ui.theme.normal14
import com.aritra.uncrack.util.UtilsKt.getAccountImage

@Composable
fun VaultCard(
    accountModel: Account,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = getAccountImage(accountModel.company),
            contentDescription = null
        )

        Column {
            Text(
                text = accountModel.company,
                style = medium18.copy(Color.Black)
            )

            Text(
                text = accountModel.email,
                style = normal14.copy(SurfaceTintLight)
            )
        }
    }
}