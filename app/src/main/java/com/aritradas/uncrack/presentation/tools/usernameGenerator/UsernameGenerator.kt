package com.aritradas.uncrack.presentation.tools.usernameGenerator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.medium30

@Composable
fun UsernameGenerator(
    usernameGeneratorViewModel: UsernameGeneratorViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val username = usernameGeneratorViewModel.username.observeAsState("")

    LaunchedEffect(Unit) {
        usernameGeneratorViewModel.generateUsername()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = username.value,
            style = medium30,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        UCButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Generate Username",
            onClick = { usernameGeneratorViewModel.generateUsername() }
        )
        UCButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.copy),
            onClick = {
                clipboardManager.setText(AnnotatedString(username.value))
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            },
            leadingIcon = painterResource(id = R.drawable.copy_password)
        )
    }
}
