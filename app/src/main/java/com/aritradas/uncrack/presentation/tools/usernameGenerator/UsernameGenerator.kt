package com.aritradas.uncrack.presentation.tools.usernameGenerator

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.ui.theme.medium30

@Composable
fun UsernameGenerator(
    navController: NavHostController,
    usernameGeneratorViewModel: UsernameGeneratorViewModel,
    modifier: Modifier = Modifier
) {
    val username = usernameGeneratorViewModel.username.observeAsState("")

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = username.value,
                style = medium30,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            UCButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = "Generate Username",
                onClick = { usernameGeneratorViewModel.generateUsername() }
            )
        }
    }
}