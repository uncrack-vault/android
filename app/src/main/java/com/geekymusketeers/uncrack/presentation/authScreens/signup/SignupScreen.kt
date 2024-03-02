package com.geekymusketeers.uncrack.presentation.authScreens.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme

class SignupScreen : ComponentActivity() {

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
                SignupContent()
            }
        }
    }
}

@Composable
fun SignupContent() {

    val name by remember {
        mutableStateOf("")
    }

    val email by remember {
        mutableStateOf("")
    }

    val password by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Sign In",
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
                value = name,
                onValueChange = {

                }
            )

            Spacer(modifier = Modifier.height(30.dp))


            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.email_header),
                hintText = stringResource(R.string.email_hint),
                value = email,
                onValueChange = {

                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.password_header),
                hintText = stringResource(R.string.password_hint),
                value = password,
                onValueChange = {},
                trailingIcon = {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.visibility_on),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Register",
                onClick = {
                    // TODO: Perform req operation and navigate to Home Screen
                },
                enabled = false
            )
        }
    }
}