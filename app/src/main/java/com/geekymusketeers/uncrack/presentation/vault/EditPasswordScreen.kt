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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPasswordScreen(
    navController: NavHostController,
    accountID: Int,
    viewPasswordViewModel: ViewPasswordViewModel,
    modifier : Modifier = Modifier
) {

    var passwordVisibility by remember { mutableStateOf(false) }
    val accountName = viewPasswordViewModel.accountModel.company
    val accountEmail = viewPasswordViewModel.accountModel.email
    val accountPassword = viewPasswordViewModel.accountModel.password
    val accountUserName = viewPasswordViewModel.accountModel.username

    LaunchedEffect(Unit) {
        viewPasswordViewModel.getAccountById(accountID)
    }

    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Edit Password",
                colors = TopAppBarDefaults.topAppBarColors(BackgroundLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                value = accountEmail,
                onValueChange = {
                    viewPasswordViewModel.updateEmail(it)
                },
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.username),
                hintText = stringResource(R.string.username_hint),
                value = accountUserName,
                onValueChange = {
                    viewPasswordViewModel.updateUserName(it)
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.password),
                hintText = stringResource(R.string.password_hint),
                value = accountPassword,
                onValueChange = {
                    viewPasswordViewModel.updatePassword(it)
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
                    // TODO: Update the logic
                    val updateAccount = Account(
                        id = 0,
                        company = "Instagram",
                        email = accountEmail,
                        category = "Social",
                        username = accountUserName,
                        password = accountPassword,
                        note = "Hello",
                        dateTime = ""
                    )
                    viewPasswordViewModel.updateAccount(updateAccount)
                },
//                enabled = isAdded
            )
        }
    }
}