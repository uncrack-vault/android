package com.aritradas.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.aritradas.uncrack.R
import com.aritradas.uncrack.ui.theme.normal12

@Composable
fun CategoryCard(
    icon: Any,
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = modifier.clickable {
            onClick()
        },
        contentAlignment = Alignment.TopEnd
    ) {

        Column(
            modifier = Modifier
                .widthIn(min = 100.dp, 100.dp)
                .heightIn(min = 100.dp, 100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.widthIn(max = 36.dp),
                model = ImageRequest.Builder(context)
                    .data(icon)
                    .crossfade(true)
                    .build(),
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                alignment = Alignment.Center
            )

            Text(
                modifier = Modifier.padding(top = 11.dp),
                text = text,
                style = normal12.copy(MaterialTheme.colorScheme.onPrimaryContainer),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun CategoryCardPreview() {
    CategoryCard(
        icon = R.drawable.social_icon,
        text = "Social"
    ) {}
}