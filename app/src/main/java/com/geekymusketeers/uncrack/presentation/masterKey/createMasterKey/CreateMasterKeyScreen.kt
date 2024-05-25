package com.geekymusketeers.uncrack.presentation.masterKey.createMasterKey

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekymusketeers.uncrack.MainActivity
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.PasswordStrengthIndicator
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.presentation.masterKey.KeyViewModel
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import com.geekymusketeers.uncrack.ui.theme.bold30
import com.geekymusketeers.uncrack.ui.theme.medium14
import com.geekymusketeers.uncrack.ui.theme.normal16
import com.geekymusketeers.uncrack.util.UtilsKt.findActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateMasterKeyScreen : ComponentActivity() {

    private lateinit var masterKeyViewModel: KeyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            )
        )

        super.onCreate(savedInstanceState)

        setContent {
            UnCrackTheme {
                masterKeyViewModel = hiltViewModel()
                CreateMasterKeyContent(this@CreateMasterKeyScreen, masterKeyViewModel)
            }
        }
    }
}


@Composable
fun CreateMasterKeyContent(
    activity: Activity,
    masterKeyViewModel: KeyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val masterKeyObserver by masterKeyViewModel.masterKeyLiveData.observeAsState("")
    val confirmMasterKeyObserver by masterKeyViewModel.confirmMasterKeyLiveData.observeAsState("")
    val hasMinLengthObserver by masterKeyViewModel.hasMinLength.observeAsState(false)
    val hasSymbolObserver by masterKeyViewModel.hasSymbol.observeAsState(false)
    val enableButtonObserver by masterKeyViewModel.enableButtonLiveData.observeAsState(false)
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Scaffold(
        modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_master_password),
                style = bold30.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.you_will_be_using_master_password_as_a_key_to_unlock_your_passwords),
                style = normal16.copy(color = SurfaceTintLight)
            )

            Spacer(modifier = Modifier.height(50.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.master_password),
                hintText = stringResource(id = R.string.password_hint),
                value = masterKeyObserver,
                onValueChange = {
                    masterKeyViewModel.setMasterKey(it)
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {

                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    val imageDescription =
                        if (passwordVisibility) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )

                    IconButton(onClick =
                    { passwordVisibility = passwordVisibility.not() }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = image,
                            contentDescription = imageDescription
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column {
                Text(
                    text = stringResource(R.string.master_password_must_include),
                    style = medium14.copy(OnPrimaryContainerLight)
                )
                Spacer(modifier = Modifier.height(10.dp))

                PasswordStrengthIndicator(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(R.string._9_or_more_characters),
                    isMet = hasMinLengthObserver
                )
                PasswordStrengthIndicator(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(R.string.at_least_1_symbol),
                    isMet = hasSymbolObserver
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.confirm_master_password),
                hintText = stringResource(id = R.string.password_hint),
                value = confirmMasterKeyObserver,
                onValueChange = {
                    masterKeyViewModel.setConfirmMasterKey(it)
                },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisibility)
                        painterResource(id = R.drawable.visibility_on)
                    else painterResource(id = R.drawable.visibility_off)

                    IconButton(onClick =
                    { confirmPasswordVisibility = confirmPasswordVisibility.not() }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = image,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    val key = Key(0, masterKeyObserver)
                    masterKeyViewModel.saveMasterKey(key)
                    context.findActivity()?.apply {
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                },
                enabled = enableButtonObserver
            )
        }
    }
}
