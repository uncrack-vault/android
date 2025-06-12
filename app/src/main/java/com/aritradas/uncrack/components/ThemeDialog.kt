package com.aritradas.uncrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDarkMode: () -> Unit,
    onLightMode: () -> Unit,
    onDynamicMode: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(28.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.themes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Change Theme",
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            Column {
                Text(
                    text = "Dark Mode",
                    modifier = Modifier
                        .clickable {
                            onDarkMode()
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Text(
                    text = "Light Mode",
                    modifier = Modifier
                        .clickable {
                            onLightMode()
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Text(
                    text = "Dynamic Theme",
                    modifier = Modifier
                        .clickable {
                            onDynamicMode()
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
            }

            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 24.dp)
            ) {
                Text(
                    text = "Dismiss",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}