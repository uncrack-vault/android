package com.geekymusketeers.uncrack.presentation.vault

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.normal20
import com.geekymusketeers.uncrack.util.UtilsKt
import com.geekymusketeers.uncrack.util.UtilsKt.generateRandomPassword
import com.geekymusketeers.uncrack.util.UtilsKt.getAccountImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPasswordScreen(
    navController: NavHostController,
    accountID: Int,
    viewPasswordViewModel: ViewPasswordViewModel,
    modifier: Modifier = Modifier
) {

    val currentDateTime = LocalDateTime.now()
    val formattedDateTime =
        DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss").format(currentDateTime)
    var passwordVisibility by remember { mutableStateOf(false) }
    val accountName = viewPasswordViewModel.accountModel.company
    val accountEmail = viewPasswordViewModel.accountModel.email
    val accountPassword = viewPasswordViewModel.accountModel.password
    val accountUserName = viewPasswordViewModel.accountModel.username
    val accountCategory = viewPasswordViewModel.accountModel.category
    val accountNote = viewPasswordViewModel.accountModel.note

    LaunchedEffect(Unit) {
        viewPasswordViewModel.getAccountById(accountID)
    }

    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Edit Password",
                colors = TopAppBarDefaults.topAppBarColors(SurfaceVariantLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceVariantLight)
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(80.dp),
                painter = getAccountImage(accountName),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = accountName,
                style = normal20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.email),
                value = accountEmail,
                onValueChange = { email ->
                    viewPasswordViewModel.updateEmail(email)
                },
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.username),
                value = accountUserName,
                onValueChange = { username ->
                    viewPasswordViewModel.updateUserName(username)
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.password),
                value = accountPassword,
                onValueChange = { password ->
                    viewPasswordViewModel.updatePassword(password)
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {

                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { passwordVisibility = passwordVisibility.not() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = image,
                                contentDescription = null
                            )
                        }

                        IconButton(onClick = {
                            val generatedUpdatedPassword = generateRandomPassword(12)
                            viewPasswordViewModel.updatePassword(generatedUpdatedPassword)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.dice),
                                contentDescription = null
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.note),
                value = accountNote,
                onValueChange = { note ->
                    viewPasswordViewModel.updateNote(note)
                }
            )


            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    val updateAccount = Account(
                        id = accountID,
                        company = accountName,
                        email = accountEmail,
                        category = accountCategory,
                        username = accountUserName,
                        password = accountPassword,
                        note = accountNote,
                        dateTime = formattedDateTime
                    )
                    viewPasswordViewModel.updateAccount(updateAccount)
                    navController.navigate(Screen.VaultScreen.name)
                }
            )
        }
    }
}