package com.aritradas.uncrack.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.NoInternetScreen
import com.aritradas.uncrack.components.ProgressDialog
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.components.UCTextField
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.presentation.auth.AuthViewModel
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.DMSansFontFamily
import com.aritradas.uncrack.ui.theme.OnPrimaryContainerLight
import com.aritradas.uncrack.ui.theme.PrimaryLight
import com.aritradas.uncrack.ui.theme.medium16
import com.aritradas.uncrack.util.ConnectivityObserver
import com.aritradas.uncrack.util.Validator.Companion.isValidEmail
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    connectivityObserver: ConnectivityObserver,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isSignInButtonEnable by remember { derivedStateOf { email.isValidEmail() } }

    val errorLiveData by viewModel.errorLiveData.observeAsState()
    val loginSuccess by viewModel.loginSuccess.observeAsState()
    var isLoading by remember { mutableStateOf(false) }
    var networkStatus by remember { mutableStateOf(ConnectivityObserver.Status.Unavailable) }

    LaunchedEffect(key1 = true) {
        connectivityObserver.observe().collectLatest { status ->
            networkStatus = status
        }
    }

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
                navController.navigate(Screen.CreateMasterKeyScreen.name)
            }
        }
    }

    when (networkStatus) {
        ConnectivityObserver.Status.Available -> {
            Scaffold(
                modifier = modifier.fillMaxSize()
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundLight)
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
                            .clickable { navController.navigate(Screen.ForgotPasswordScreen.name) },
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    UCButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.login),
                        isLoading = isLoading,
                        loadingText = "Logging you in..",
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
                            modifier = Modifier.clickable { navController.navigate(Screen.SignUpScreen.name) },
                            text = stringResource(R.string.create),
                            style = medium16.copy(color = PrimaryLight)
                        )
                    }
                }
            }
        }
        else -> {
            NoInternetScreen()
        }
    }
}