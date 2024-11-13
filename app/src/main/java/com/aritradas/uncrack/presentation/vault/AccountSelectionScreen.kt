package com.aritradas.uncrack.presentation.vault

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.AccountCard
import com.aritradas.uncrack.components.UCTopAppBar
import com.aritradas.uncrack.presentation.vault.viewmodel.AddEditViewModel
import com.aritradas.uncrack.ui.theme.SurfaceVariantLight
import com.aritradas.uncrack.ui.theme.medium20
import com.aritradas.uncrack.util.UtilsKt.getCommunicationAccounts
import com.aritradas.uncrack.util.UtilsKt.getCommunitiesAccounts
import com.aritradas.uncrack.util.UtilsKt.getCrowdSourcingAccounts
import com.aritradas.uncrack.util.UtilsKt.getPortfolioAccounts
import com.aritradas.uncrack.util.UtilsKt.getSocialAccounts
import com.aritradas.uncrack.util.UtilsKt.getWorkAccounts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelectionScreen(
    navController: NavHostController,
    addEditViewModel: AddEditViewModel,
    modifier: Modifier = Modifier,
    goToAddPasswordScreen: (Int, String, String) -> Unit
) {
    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            UCTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Add Account",
                onBackPress = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.social),
                    style = medium20.copy(Color.Black)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(getSocialAccounts().size) { index ->
                val entry = getSocialAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(id = R.string.social)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.work),
                    style = medium20.copy(Color.Black)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(getWorkAccounts().size) { index ->
                val entry = getWorkAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(id = R.string.work)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.crowdsourcing),
                    style = medium20.copy(Color.Black)
                )

                Spacer(modifier = Modifier.height(10.dp))
            }


            items(getCrowdSourcingAccounts().size) { index ->
                val entry = getCrowdSourcingAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(R.string.crowdsourcing)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.communication),
                    style = medium20.copy(Color.Black)
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            items(getCommunicationAccounts().size) { index ->
                val entry = getCommunicationAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(R.string.communication)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }


            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.portfolio),
                    style = medium20.copy(Color.Black)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }


            items(getPortfolioAccounts().size) { index ->
                val entry = getPortfolioAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(R.string.portfolio)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }


            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.communities),
                    style = medium20.copy(Color.Black)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(getCommunitiesAccounts().size) { index ->
                val entry = getCommunitiesAccounts().entries.elementAt(index)
                val accountText = entry.key
                val iconId = entry.value
                val category = stringResource(R.string.communities)

                AccountCard(
                    icon = iconId,
                    text = accountText.text,
                ) {
                    addEditViewModel.resetState()
                    goToAddPasswordScreen(iconId, accountText.text, category)
                }
            }
        }
    }
}
