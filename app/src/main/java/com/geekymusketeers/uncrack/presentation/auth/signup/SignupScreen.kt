package com.geekymusketeers.uncrack.presentation.auth.signup

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
import com.geekymusketeers.uncrack.components.ProgressDialog
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.presentation.auth.AuthViewModel
import com.geekymusketeers.uncrack.presentation.auth.login.LoginScreens
import com.geekymusketeers.uncrack.presentation.masterKey.createMasterKey.CreateMasterKeyScreen
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryLight
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import com.geekymusketeers.uncrack.ui.theme.medium16
import com.geekymusketeers.uncrack.util.UtilsKt.findActivity
import com.geekymusketeers.uncrack.util.UtilsKt.isNetworkAvailable
import com.geekymusketeers.uncrack.util.Validator.Companion.isValidEmail
import com.geekymusketeers.uncrack.util.Validator.Companion.isValidName
import com.geekymusketeers.uncrack.util.Validator.Companion.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupScreen : ComponentActivity() {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private lateinit var authViewModel: AuthViewModel
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
                var newUser by remember { mutableStateOf(auth.currentUser) }
                authViewModel = hiltViewModel()
                SignupContent(
                    this@SignupScreen,
                    authViewModel,
                    onSignUp = { signUpUser ->
                        newUser = signUpUser
                    }
                )
            }
        }
    }
}

@Composable
fun SignupContent(
    activity: Activity,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onSignUp: (FirebaseUser) -> Unit
) {

    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isRegisterButtonEnable by remember {
        derivedStateOf {
            userName.isValidName() && userEmail.isValidEmail() && userPassword.isValidPassword()
        }
    }
    val errorLiveData by authViewModel.errorLiveData.observeAsState()
    val registerStatus by authViewModel.registerStatus.observeAsState(false)
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(registerStatus) {
        if (registerStatus) {
            isLoading = false
            Toast.makeText(
                context,
                context.getString(R.string.account_created_successfully), Toast.LENGTH_SHORT
            ).show()
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
                text = stringResource(R.string.sign_up),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = DMSansFontFamily,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(60.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.name_header),
                hintText = stringResource(R.string.name_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                value = userName,
                onValueChange = { userName = it }
            )

            Spacer(modifier = Modifier.height(30.dp))


            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.email_header),
                hintText = stringResource(R.string.email_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                value = userEmail,
                onValueChange = { userEmail = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.password_header),
                hintText = stringResource(R.string.password_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                value = userPassword,
                onValueChange = { userPassword = it },
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

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.register),
                onClick = {
                    if (context.isNetworkAvailable()) {
                        isLoading = true
                        authViewModel.signUp(
                            userName,
                            userEmail,
                            userPassword,
                            onSignedUp = { signUpUser ->
                                onSignUp(signUpUser)
                            }
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "No Internet avaialble",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    context.findActivity()?.apply {
                        startActivity(Intent(activity, CreateMasterKeyScreen::class.java))
                    }
                },
                enabled = isRegisterButtonEnable
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
                    style = medium16.copy(color = OnPrimaryContainerLight)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.clickable {
                        context.findActivity()?.apply {
                            startActivity(Intent(activity, LoginScreens::class.java))
                        }
                    },
                    text = stringResource(id = R.string.login),
                    style = medium16.copy(color = PrimaryLight)
                )
            }
        }
    }
}