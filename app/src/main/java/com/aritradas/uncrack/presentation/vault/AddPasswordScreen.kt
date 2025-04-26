package com.aritradas.uncrack.presentation.vault

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.components.UCTextField
import com.aritradas.uncrack.components.UCTopAppBar
import com.aritradas.uncrack.domain.model.Account
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.presentation.vault.viewmodel.AddEditViewModel
import com.aritradas.uncrack.ui.theme.BackgroundLight
import com.aritradas.uncrack.ui.theme.medium22
import com.aritradas.uncrack.util.UtilsKt.generateRandomPassword
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun AddPasswordScreen(
    navController: NavHostController,
    accountIcon: Int,
    accountName: String,
    accountCategory: String,
    addEditViewModel: AddEditViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        addEditViewModel.resetState()
    }

    val currentDateTime = LocalDateTime.now()
    val formattedDateTime =
        DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss").format(currentDateTime)
    var passwordVisibility by remember { mutableStateOf(false) }
    val email by addEditViewModel.email.observeAsState("")
    val username by addEditViewModel.username.observeAsState("")
    val password by addEditViewModel.password.observeAsState("")
    val note by addEditViewModel.note.observeAsState("")
    val isAdded by addEditViewModel.isAdded.observeAsState(false)

    Scaffold(
        modifier
            .fillMaxSize()
            .imePadding(),
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = rememberAsyncImagePainter(model = accountIcon),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = accountName,
                style = medium22.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.email),
                hintText = "user@example.com",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                value = email,
                onValueChange = {
                    addEditViewModel.setEmail(it)
                },
                trailingIcon = {
                    if (email.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                addEditViewModel.setEmail("")
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.username),
                hintText = "user.example",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                value = username,
                onValueChange = {
                    addEditViewModel.setUserName(it)
                },
                trailingIcon = {
                    if (username.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                addEditViewModel.setUserName("")
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                headerText = stringResource(id = R.string.password),
                hintText = "Hit dice to generate password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                value = password,
                onValueChange = {
                    addEditViewModel.setPassword(it)
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (password.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    addEditViewModel.setPassword("")
                                }
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = null,
                                )
                            }
                        }

                        IconButton(onClick = { passwordVisibility = passwordVisibility.not() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = image,
                                contentDescription = null
                            )
                        }

                        IconButton(onClick = {
                            val generatedPassword = generateRandomPassword(12)
                            addEditViewModel.setPassword(generatedPassword)
                            passwordVisibility = true
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                value = note,
                onValueChange = {
                    addEditViewModel.setNote(it)
                },
                trailingIcon = {
                    if (note.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                addEditViewModel.setNote("")
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            UCButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                onClick = {
                    val account = Account(
                        id = 0,
                        company = accountName,
                        email = email,
                        category = accountCategory,
                        username = username,
                        password = password,
                        note = note,
                        dateTime = formattedDateTime
                    )
                    addEditViewModel.addAccount(account)
                    navController.navigate(Screen.VaultScreen.name)
                },
                enabled = isAdded
            )
        }
    }
}