package com.aritradas.uncrack.presentation.tools.passwordGenerator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.OnPrimaryContainerLight
import com.aritradas.uncrack.ui.theme.PrimaryLight
import com.aritradas.uncrack.ui.theme.SurfaceLight
import com.aritradas.uncrack.ui.theme.medium24
import com.aritradas.uncrack.ui.theme.medium30
import com.aritradas.uncrack.ui.theme.normal16
import com.aritradas.uncrack.ui.theme.oldPassword
import com.aritradas.uncrack.ui.theme.strongPassword
import com.aritradas.uncrack.ui.theme.weakPassword
import com.aritradas.uncrack.util.Constants.sliderStepRange
import com.aritradas.uncrack.util.Constants.sliderSteps
import com.aritradas.uncrack.util.UtilsKt.calculatePasswordStrength
import timber.log.Timber

@Composable
fun PasswordGenerator(
    passwordGeneratorViewModel: PasswordGeneratorViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val password by passwordGeneratorViewModel.password.observeAsState("")
    val passwordLength by passwordGeneratorViewModel.passwordLength.observeAsState(0.0f)
    var passwordStrength by remember { mutableIntStateOf(0) }
    var progressValue by remember { mutableFloatStateOf(0f) }
    val includeUppercase by passwordGeneratorViewModel.includeUppercase.observeAsState(true)
    val includeLowercase by passwordGeneratorViewModel.includeLowercase.observeAsState(true)
    val includeNumbers by passwordGeneratorViewModel.includeNumbers.observeAsState(true)
    val includeSpecialChars by passwordGeneratorViewModel.includeSpecialChars.observeAsState(true)

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        passwordGeneratorViewModel.generatePassword()
    }

    LaunchedEffect(password) {
        passwordStrength = calculatePasswordStrength(password)
        val mappedScore = (passwordStrength * 100) / 9
        progressValue = mappedScore.toFloat()
        Timber.d("Progress value $progressValue")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                password.forEach {
                    val textColor = when {
                        it.isDigit() -> Color.Blue
                        it.isLetterOrDigit().not() -> Color.Magenta
                        else -> MaterialTheme.colorScheme.onPrimaryContainer
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

        val lineColor = when {
            passwordStrength <= 3 -> weakPassword
            passwordStrength <= 7 -> oldPassword
            else -> strongPassword
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(lineColor)
        )

        UCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.copy),
            onClick = {
                password.let { passwordToCopy ->
                    clipboardManager.setText(AnnotatedString((passwordToCopy)))
                }
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            },
            leadingIcon = painterResource(id = R.drawable.copy_password)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.password_generated_length, passwordLength.toInt()),
            style = medium24.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = passwordLength,
            onValueChange = { newPasswordLength ->
                passwordGeneratorViewModel.updatePasswordLength(newPasswordLength)
            },
            onValueChangeFinished = {
                passwordGeneratorViewModel.generatePassword()
            },
            steps = sliderSteps,
            valueRange = sliderStepRange,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.surface,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.include_following),
            style = medium24.copy(OnPrimaryContainerLight)
        )

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
