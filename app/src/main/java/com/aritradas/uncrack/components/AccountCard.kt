package com.aritradas.uncrack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.R
import com.aritradas.uncrack.ui.theme.SurfaceVariantLight
import com.aritradas.uncrack.ui.theme.medium18

@Composable
fun AccountCard(
    icon: Int,
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(SurfaceVariantLight)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = medium18.copy(Color.Black)
        )
    }
}

@Preview
@Composable
private fun AccountCardPreview() {
    AccountCard(icon = R.drawable.instagram, text = "Instagram", onClick = {})
}