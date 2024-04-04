package com.geekymusketeers.uncrack.presentation.vault

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.navigation.Screen
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.ViewPasswordViewModel
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.normal12
import com.geekymusketeers.uncrack.ui.theme.normal30
import com.geekymusketeers.uncrack.util.UtilsKt.calculatePasswordStrength
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordScreen(
    navController: NavHostController,
    accountId: Int,
    viewPasswordViewModel: ViewPasswordViewModel,
    modifier: Modifier = Modifier
) {

    var passwordStrength by remember { mutableIntStateOf(0) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var progressValue by remember { mutableFloatStateOf(0f) }
    var progressMessage by rememberSaveable { mutableStateOf("") }
    val username = viewPasswordViewModel.accountModel.username
    val password = viewPasswordViewModel.accountModel.password

    LaunchedEffect(Unit) {
        viewPasswordViewModel.getAccountById(accountId)
    }

    LaunchedEffect(password) {
        passwordStrength = calculatePasswordStrength(password)
        Timber.d("Strength $passwordStrength")
        val mappedScore = (passwordStrength * 100) / 9
        progressValue = mappedScore.toFloat()
        Timber.d("Progress value $progressValue")
        progressMessage = passwordStrength.toString()
        Timber.d("Progress message $progressMessage")
    }

    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "",
                onBackPress = { navController.popBackStack() },
                shouldShowFavAndEditButton = false,
                onEditPress = { navController.navigate(Screen.EditPasswordScreen.name) },
                onDeletePress = { }
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
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = R.drawable.new_medium),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Medium",
                style = normal30.copy(Color.Black)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.category_social),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Social",
                        style = normal12.copy(OnSurfaceVariantLight)
                    )
                }

                VerticalDivider(
                    modifier = Modifier.height(30.dp),
                    thickness = 1.dp,
                    color = SurfaceTintLight.copy(alpha = .5f)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.Center) {

                        CircularProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier.size(42.dp),
                            color = when {
                                passwordStrength <= 3 -> Color.Red
                                passwordStrength <= 7 -> Color.Yellow
                                else -> Color.Green
                            },
                            strokeWidth = 5.dp,
                            trackColor = SurfaceTintLight,
                            strokeCap = StrokeCap.Round,
                        )

                        Text(
                            text = progressMessage,
                            style = normal12,
                            color = OnPrimaryContainerLight,
                        )

                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = stringResource(R.string.password_strength),
                        style = normal12.copy(OnSurfaceVariantLight)
                    )
                }

                VerticalDivider(
                    modifier = Modifier.height(30.dp),
                    thickness = 1.dp,
                    color = SurfaceTintLight.copy(alpha = .5f)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.outlined_fav_btn),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = stringResource(R.string.not_favourite),
                        style = normal12.copy(OnSurfaceVariantLight)
                    )

                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.username),
                hintText = stringResource(R.string.username_hint),
                value = username,
                onValueChange = {},
                readOnly = true
            )

            Spacer(modifier = Modifier.height(21.dp))

            UCTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                headerText = stringResource(id = R.string.password),
                hintText = stringResource(R.string.password_hint),
                value = password,
                onValueChange = {},
                readOnly = true,
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
        }
    }
}
