package com.aritradas.uncrack.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.aritradas.uncrack.R
import com.aritradas.uncrack.ui.theme.DMSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UCTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    shouldShowBackButton: Boolean = true,
    shouldShowFavAndEditButton: Boolean = true,
    fontSize: TextUnit = 22.sp,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface
    ),
    onBackPress: () -> Unit = {},
    onEditPress: () -> Unit = {},
    onDeletePress: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontFamily = DMSansFontFamily,
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        navigationIcon = {
            if (shouldShowBackButton) {
                IconButton(onClick = { onBackPress() }) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (!shouldShowFavAndEditButton) {
                IconButton(onClick = { onEditPress() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { onDeletePress() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = null
                    )
                }
            }
        },
        colors = colors
    )
}