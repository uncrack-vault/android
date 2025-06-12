package com.aritradas.uncrack.presentation.vault

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.components.UCTextField
import com.aritradas.uncrack.components.UCTopAppBar
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import com.aritradas.uncrack.ui.theme.medium22
import com.aritradas.uncrack.util.UtilsKt.generateRandomPassword
import com.aritradas.uncrack.util.UtilsKt.getAccountImage
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

    val accountName = viewPasswordViewModel.accountModel?.company
    val accountEmail = viewPasswordViewModel.decryptedEmail
    val accountPassword = viewPasswordViewModel.decryptedPassword
    val accountUserName = viewPasswordViewModel.decryptedUsername
    val accountCategory = viewPasswordViewModel.accountModel?.category
    val accountNote = viewPasswordViewModel.accountModel?.note

    LaunchedEffect(Unit) {
        viewPasswordViewModel.getAccountById(accountID)
    }

    Scaffold(
        modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Edit Password",
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(100.dp),
                painter = getAccountImage(accountName.toString()),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = accountName.toString(),
                style = medium22.copy(MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(20.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.email),
                hintText = "user@example.com",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
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
                hintText = "user.example",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
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
                hintText = "Hit dice to generate password",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
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
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                value = accountNote.toString(),
                onValueChange = { note ->
                    viewPasswordViewModel.updateNote(note)
                }
            )


            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    val updateAccount = Account(
                        id = accountID,
                        company = accountName.toString(),
                        email = accountEmail,
                        category = accountCategory.toString(),
                        username = accountUserName,
                        password = accountPassword,
                        note = accountNote.toString(),
                        dateTime = formattedDateTime
                    )
                    viewPasswordViewModel.updateAccount(updateAccount)
                    navController.navigate(Screen.VaultScreen.name)
                }
            )
        }
    }
}