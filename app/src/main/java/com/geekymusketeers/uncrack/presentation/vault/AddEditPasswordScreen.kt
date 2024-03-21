package com.geekymusketeers.uncrack.presentation.vault

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPasswordScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    var passwordVisibility by remember { mutableStateOf(false) }

    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Add Password",
                colors = TopAppBarDefaults.topAppBarColors(BackgroundLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Image(
                modifier = Modifier.size(110.dp),
                painter = rememberAsyncImagePainter(model = R.drawable.new_medium),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(20.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.email),
                hintText = stringResource(id = R.string.email_hint),
                value = "",
                onValueChange = {

                },
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.username),
                hintText = stringResource(R.string.username_hint),
                value = "",
                onValueChange = {
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.password),
                hintText = stringResource(R.string.password_hint),
                value = "",
                onValueChange = {
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {

                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_on)
                    else painterResource(id = R.drawable.visibility_off)

                    IconButton(onClick =
                    { passwordVisibility = passwordVisibility.not() }
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

                },
            )
        }
    }
}