package com.geekymusketeers.uncrack.presentation.account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceLight
import com.geekymusketeers.uncrack.ui.theme.medium24
import com.geekymusketeers.uncrack.ui.theme.medium30
import com.geekymusketeers.uncrack.ui.theme.normal16
import com.geekymusketeers.uncrack.util.Constants.sliderStepRange
import com.geekymusketeers.uncrack.util.Constants.sliderSteps
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGenerator(
    navController: NavHostController,
    passwordGeneratorViewModel: PasswordGeneratorViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val password by passwordGeneratorViewModel.password.observeAsState("")
    val passwordLength by passwordGeneratorViewModel.passwordLength.observeAsState(0.0f)
    val includeUppercase by passwordGeneratorViewModel.includeUppercase.observeAsState(true)
    val includeLowercase by passwordGeneratorViewModel.includeLowercase.observeAsState(true)
    val includeNumbers by passwordGeneratorViewModel.includeNumbers.observeAsState(true)
    val includeSpecialChars by passwordGeneratorViewModel.includeSpecialChars.observeAsState(true)

    Scaffold(
        topBar = {
            UCTopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = "Password Generator",
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(BackgroundLight),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    password.forEach {
                        val textColor = when {
                            it.isDigit() -> Color.Blue
                            it.isLetterOrDigit().not() -> Color.Magenta
                            else -> OnPrimaryContainerLight
                        }
                        withStyle(style = SpanStyle(color = textColor)) {
                            append(it.toString())
                        }
                    }
                },
                style = medium30,
                textAlign = TextAlign.Center,
                letterSpacing = TextUnit(1F, TextUnitType.Sp)
            )

            Spacer(modifier = Modifier.height(90.dp))

            Text(
                text = stringResource(R.string.password_generated_length, passwordLength.toInt()),
                style = medium24.copy(OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = passwordLength,
                onValueChange = { newPasswordLength ->
                    passwordGeneratorViewModel.updatePasswordLength(newPasswordLength)
                },
                steps = sliderSteps,
                valueRange = sliderStepRange,
                colors = SliderDefaults.colors(
                    thumbColor = OnPrimaryContainerLight,
                    activeTrackColor = PrimaryLight,
                    inactiveTrackColor = SurfaceLight,
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.include_following),
                style = medium24.copy(OnPrimaryContainerLight)
            )

            Spacer(modifier = Modifier.height(15.dp))

            SwitchItem(
                label = stringResource(R.string.numbers),
                checked = includeNumbers
            ) {
                passwordGeneratorViewModel.updateIncludeNumbers(it)
            }
            SwitchItem(
                label = stringResource(R.string.uppercase_letters),
                checked = includeUppercase
            ) {
                passwordGeneratorViewModel.updateIncludeUppercase(it)
            }
            SwitchItem(
                label = stringResource(R.string.lowercase_letters),
                checked = includeLowercase
            ) {
                passwordGeneratorViewModel.updateIncludeLowercase(it)
            }
            SwitchItem(
                label = stringResource(R.string.special_symbols),
                checked = includeSpecialChars
            ) {
                passwordGeneratorViewModel.updateIncludeSpecialChars(it)
            }

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.generate),
                onClick = {
                    passwordGeneratorViewModel.generatePassword()
                    Timber.d("Password ${passwordGeneratorViewModel.generatePassword()}")
                },
                leadingIcon = painterResource(id = R.drawable.generate_password)
            )

            Spacer(modifier = Modifier.height(10.dp))

            UCButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.copy),
                onClick = {
                    password.let { passwordToCopy ->
//                        clipboardManager.setText() //TODO: Need to check
                    }
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                },
                leadingIcon = painterResource(id = R.drawable.copy_password)
            )
        }
    }
}


@Composable
fun SwitchItem(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = normal16.copy(OnPrimaryContainerLight)
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
