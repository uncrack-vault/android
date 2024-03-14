package com.geekymusketeers.uncrack.components

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
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.ui.theme.light16
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.normal24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDialog(
    onDismissRequest: () -> Unit
) {

    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            Modifier
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
                style = normal24.copy(MaterialTheme.colorScheme.onSurface)
            )

            Column {


                Text(
                    text = "Dark Mode",
                    modifier = Modifier
                        .clickable {
                            // TODO: Impl the logic
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = light16.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Text(
                    text = "Light Mode",
                    modifier = Modifier
                        .clickable {
                            // TODO: Impl the logic
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = light16.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Text(
                    text = "Dynamic Theme",
                    modifier = Modifier
                        .clickable {
                            // TODO: Impl the logic
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    style = light16.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
            }

            TextButton(
                onClick = {
                    onDismissRequest()
                },
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
                    style = medium14
                )
            }
        }
    }
}