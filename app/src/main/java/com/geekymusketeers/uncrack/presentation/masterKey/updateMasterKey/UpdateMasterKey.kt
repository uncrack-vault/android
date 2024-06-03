package com.geekymusketeers.uncrack.presentation.masterKey.updateMasterKey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.presentation.masterKey.KeyViewModel
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMasterKey(
    navController: NavHostController,
    keyViewModel: KeyViewModel,
    modifier: Modifier = Modifier
) {

    var oldMasterKey by remember { mutableStateOf("") }
    var newMasterKey by remember { mutableStateOf("") }
    var newConfirmMasterKey by remember { mutableStateOf("") }
    var oldPasswordVisibility by remember { mutableStateOf(false) }
    var newPasswordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    val oldMasterPasswordObserver = keyViewModel.keyModel.password


    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Update Master Password",
                colors = TopAppBarDefaults.topAppBarColors(SurfaceVariantLight),
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp)
        ) {

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.old_master_password),
                hintText = stringResource(id = R.string.password_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                value = oldMasterKey,
                onValueChange = { oldMasterKey = it },
                visualTransformation = if (oldPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (oldPasswordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    val imageDescription =
                        if (oldPasswordVisibility) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )


                    IconButton(onClick = { oldPasswordVisibility = oldPasswordVisibility.not() }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = image,
                            contentDescription = imageDescription
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(R.string.new_master_password),
                hintText = stringResource(id = R.string.password_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                value = newMasterKey,
                onValueChange = { newMasterKey = it },
                visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (newPasswordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    val imageDescription =
                        if (newPasswordVisibility) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )

                    IconButton(onClick = { newPasswordVisibility = newPasswordVisibility.not() }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = image,
                            contentDescription = imageDescription
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.confirm_master_password),
                hintText = stringResource(id = R.string.password_hint),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                value = newConfirmMasterKey,
                onValueChange = { newConfirmMasterKey = it },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisibility)
                        painterResource(id = R.drawable.visibility_off)
                    else painterResource(id = R.drawable.visibility_on)

                    val imageDescription =
                        if (confirmPasswordVisibility) stringResource(R.string.show_password) else stringResource(
                            R.string.hide_password
                        )

                    IconButton(onClick = { confirmPasswordVisibility = confirmPasswordVisibility.not() }
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
                text = stringResource(id = R.string.update),
                onClick = {
                    val updatedKey = Key(0, newMasterKey)
                    keyViewModel.saveMasterKey(updatedKey)
                    navController.popBackStack()
                },
                enabled = oldMasterPasswordObserver == oldMasterKey &&
                        newMasterKey.length >= 8 &&
                        newMasterKey == newConfirmMasterKey
            )
        }
    }
}
