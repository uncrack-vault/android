package com.geekymusketeers.uncrack.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.ProfileContainer
import com.geekymusketeers.uncrack.components.UCButton
import com.geekymusketeers.uncrack.components.UCTextField
import com.geekymusketeers.uncrack.components.UCTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            UCTopAppBar(
                title = "Profile",
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.White
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(bottom = 20.dp)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProfileContainer {

                }

                Spacer(modifier = Modifier.height(20.dp))

                UCTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    headerText = "Name",
                    hintText = stringResource(id = R.string.name_hint),
                    value = "",
                    onValueChange = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                UCTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    headerText = "Email",
                    hintText = stringResource(id = R.string.email_hint),
                    value = "",
                    onValueChange = {}
                )

                Spacer(modifier = Modifier.weight(1f))

                UCButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp,0.dp),
                    text = stringResource(id = R.string.save),
                    onClick = {}
                )
            }
        }
    }
}