package com.geekymusketeers.uncrack.presentation.vault

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import coil.compose.rememberAsyncImagePainter
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.domain.model.Account
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.presentation.account.PasswordGeneratorViewModel
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.AddEditViewModel
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.normal24
import com.geekymusketeers.uncrack.util.UtilsKt.generateRandomPassword
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordScreen(
    navController: NavHostController,
    accountIcon: Int,
    accountName: String,
    addEditViewModel: AddEditViewModel,
    modifier: Modifier = Modifier
) {

    val currentDateTime = LocalDateTime.now()
    val formattedDateTime = DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss").format(currentDateTime)
    var passwordVisibility by remember { mutableStateOf(false) }
    val email by addEditViewModel.email.observeAsState("")
    val username by addEditViewModel.username.observeAsState("")
    val password by addEditViewModel.password.observeAsState("")
    val note by addEditViewModel.note.observeAsState("")
    val isAdded by addEditViewModel.isAdded.observeAsState(false)

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(110.dp),
                painter = rememberAsyncImagePainter(model = accountIcon),
                contentDescription = null
            )

            Text(
                text = accountName,
                style = normal24.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.email),
                value = email,
                onValueChange = {
                    addEditViewModel.setEmail(it)
                },
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.username),
                value = username,
                onValueChange = {
                    addEditViewModel.setUserName(it)
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.password),
                value = password,
                onValueChange = {
                    addEditViewModel.setPassword(it)
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {

                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_on)
                    else painterResource(id = R.drawable.visibility_off)

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

                        IconButton(onClick = { generateRandomPassword(12) }) {
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
                value = note,
                onValueChange = {
                    addEditViewModel.setNote(it)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    val account = Account(
                        id = 0,
                        company = accountName,
                        email = email,
                        category = "Social",
                        username = username,
                        password = password,
                        note = note,
                        dateTime = formattedDateTime
                    )
                    addEditViewModel.addAccount(account)
                    navController.navigate(Screen.VaultScreen.name)
                },
//                enabled = isAdded
            )
        }
    }
}