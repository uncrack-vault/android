package com.aritra.uncrack.presentation.auth.forgotPassword

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aritra.uncrack.R
import com.aritra.uncrack.components.NoInternetScreen
import com.aritra.uncrack.components.ProgressDialog
import com.aritra.uncrack.components.UCButton
import com.aritra.uncrack.components.UCTextField
import com.aritra.uncrack.presentation.auth.AuthViewModel
import com.aritra.uncrack.ui.theme.DMSansFontFamily
import com.aritra.uncrack.ui.theme.UnCrackTheme
import com.aritra.uncrack.util.ConnectivityObserver
import com.aritra.uncrack.util.NetworkConnectivityObserver
import com.aritra.uncrack.util.UtilsKt
import com.aritra.uncrack.util.UtilsKt.findActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ForgotPasswordScreen : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

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
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        setContent {
            UnCrackTheme {
                var networkStatus by remember {
                    mutableStateOf(ConnectivityObserver.Status.Unavailable)
                }

                LaunchedEffect(key1 = true) {
                    connectivityObserver.observe().collectLatest { status ->
                        networkStatus = status
                    }
                }

                when(networkStatus) {
                    ConnectivityObserver.Status.Available -> {
                        ForgotPasswordContent()
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
fun ForgotPasswordContent(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    val enableSendBtn by remember { derivedStateOf { UtilsKt.validateEmail(email) } }
    val resetPasswordRequestLiveData by viewModel.resetPassword.observeAsState()
    val errorLiveData by viewModel.errorLiveData.observeAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(resetPasswordRequestLiveData) {
        if (resetPasswordRequestLiveData == true) {
            isLoading = false
            context.findActivity()?.let {
                Toast.makeText(it, "Email sent to your registered email", Toast.LENGTH_LONG).show()
                it.finish()
            }
        }
    }

    LaunchedEffect(errorLiveData) {
        errorLiveData?.let { error ->
            isLoading = false
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    if (isLoading) {
        ProgressDialog {}
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
                text = stringResource(R.string.forgot_password),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = DMSansFontFamily,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(60.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.enter_your_registered_mail),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.send_reset_link),
                onClick = {
                    viewModel.resetPassword(email)
                },
                enabled = enableSendBtn
            )
        }
    }
}
