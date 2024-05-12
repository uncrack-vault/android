package com.geekymusketeers.uncrack.presentation.masterKey

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.MainActivity
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.domain.model.Key
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.presentation.auth.login.LoginScreens
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import com.geekymusketeers.uncrack.ui.theme.bold30
import com.geekymusketeers.uncrack.util.UtilsKt.findActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateMasterKeyScreen: ComponentActivity() {

    private lateinit var masterKeyViewModel: KeyViewModel
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
                masterKeyViewModel = hiltViewModel()
                CreateMasterKeyContent(this@CreateMasterKeyScreen, masterKeyViewModel)
            }
        }
    }
}



@Composable
fun CreateMasterKeyContent(
    activity: Activity,
    masterKeyViewModel: KeyViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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
                    context.findActivity()?.apply {
                        startActivity(Intent(activity, MainActivity::class.java))
                    }
                },
                enabled = enableButtonObserver
            )
        }
    }
}
