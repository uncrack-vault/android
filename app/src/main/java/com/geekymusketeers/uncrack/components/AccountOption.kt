package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.geekymusketeers.uncrack.presentation.account.AccountItems
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceLight
import com.geekymusketeers.uncrack.ui.theme.normal16

@Composable
fun AccountOption(
    accountItem: AccountItems,
    modifier: Modifier = Modifier,
    onClick: (AccountItems) -> Unit
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(accountItem) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(accountItem.icon)
                .size(coil.size.Size.ORIGINAL)
                .build(), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = accountItem.itemsName,
            style = normal16.copy(color = OnSurfaceLight)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemPreview() {
    AccountOption(AccountItems.BIOMETRIC) {
        // Do something
    }
}