package com.geekymusketeers.uncrack.presentation.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.NoInternetScreen
import com.geekymusketeers.uncrack.components.ProgressDialog
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.presentation.auth.AuthViewModel
import com.geekymusketeers.uncrack.presentation.auth.forgotPassword.ForgotPasswordScreen
import com.geekymusketeers.uncrack.presentation.auth.signup.SignupScreen
import com.geekymusketeers.uncrack.presentation.masterKey.createMasterKey.CreateMasterKeyScreen
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryLight
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import com.geekymusketeers.uncrack.ui.theme.medium16
import com.geekymusketeers.uncrack.util.ConnectivityObserver
import com.geekymusketeers.uncrack.util.NetworkConnectivityObserver
import com.geekymusketeers.uncrack.util.UtilsKt.findActivity
import com.geekymusketeers.uncrack.util.Validator.Companion.isValidEmail
import com.geekymusketeers.uncrack.util.Validator.Companion.isValidPassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginScreens : ComponentActivity() {

    private lateinit var userAuthViewModel: AuthViewModel
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            UnCrackTheme {
                var networkStatus by remember { mutableStateOf(ConnectivityObserver.Status.Unavailable) }

                LaunchedEffect(key1 = true) {
                    connectivityObserver.observe().collectLatest { status ->
                        networkStatus = status
                    }
                }
                userAuthViewModel = hiltViewModel()

                when (networkStatus) {
                    ConnectivityObserver.Status.Available -> {
                        LoginContent(this@LoginScreens, userAuthViewModel)
                    }
                    else -> {
                        NoInternetScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun LoginContent(
    activity: Activity,
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isSignInButtonEnable by remember {
        derivedStateOf {
            email.isValidEmail() && password.isValidPassword()
        }
    }

    val errorLiveData by viewModel.errorLiveData.observeAsState()
    val loginSuccess by viewModel.loginSuccess.observeAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(errorLiveData) {
        errorLiveData?.let { error ->
            isLoading = false
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(loginSuccess) {
        loginSuccess?.let { success ->
            isLoading = false
            if (success) {
                context.findActivity()?.apply {
                    startActivity(Intent(activity, CreateMasterKeyScreen::class.java))
                    finish() // Optional: close the login activity
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.log_in),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = DMSansFontFamily,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(60.dp))

            UCTextField(
                modifier = Modifier.fillMaxWidth(),
                headerText = stringResource(R.string.email_header),
                hintText = stringResource(R.string.email_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier.fillMaxWidth(),
                headerText = stringResource(R.string.password_header),
                hintText = stringResource(R.string.password_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    val imageDescription =
                        if (passwordVisibility) stringResource(R.string.show_password) else stringResource(R.string.hide_password)

                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = image,
                            contentDescription = imageDescription
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        context.findActivity()?.let {
                            it.startActivity(Intent(it, ForgotPasswordScreen::class.java))
                        }
                    },
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login),
                onClick = {
                    isLoading = true
                    viewModel.logIn(email, password)
                },
                enabled = isSignInButtonEnable
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    style = medium16.copy(color = OnPrimaryContainerLight)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.clickable {
                        context.findActivity()?.apply {
                            startActivity(Intent(activity, SignupScreen::class.java))
                        }
                    },
                    text = stringResource(R.string.create),
                    style = medium16.copy(color = PrimaryLight)
                )
            }
        }
    }

    if (isLoading) {
        ProgressDialog {}
    }
}