package com.geekymusketeers.uncrack.presentation.masterKey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.ui.theme.bold30
import com.geekymusketeers.uncrack.viewModel.KeyViewModel

@Composable
fun CreateMasterKeyScreen(
    navController: NavHostController,
    masterKeyViewModel: KeyViewModel,
    modifier: Modifier = Modifier
) {

    val masterKeyObserver by masterKeyViewModel.masterKeyLiveData.observeAsState("")
    val confirmMasterKeyObserver by masterKeyViewModel.confirmMasterKeyLiveData.observeAsState("")
    val enableButtonObserver by masterKeyViewModel.enableButtonLiveData.observeAsState(false)
    var passwordVisibility by remember { mutableStateOf(false) }


    Scaffold(
        modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Create Master Key",
                style = bold30.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(50.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.master_key),
                hintText = stringResource(id = R.string.password_hint),
                value = masterKeyObserver,
                onValueChange = {
                    masterKeyViewModel.setMasterKey(it)
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

            Spacer(modifier = Modifier.height(30.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.confirm_master_key),
                hintText = stringResource(id = R.string.password_hint),
                value = confirmMasterKeyObserver,
                onValueChange = {
                    masterKeyViewModel.setConfirmMasterKey(it)
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
                    val key = Key(0,masterKeyObserver)
                    masterKeyViewModel.saveMasterKey(key)
                    navController.navigate("home_screen")
                },
                enabled = enableButtonObserver.not()
            )
        }
    }
}
