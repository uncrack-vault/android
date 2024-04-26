package com.geekymusketeers.uncrack.presentation.vault

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.geekymusketeers.uncrack.components.AccountCard
import com.geekymusketeers.uncrack.components.UCTopAppBar
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.medium20
import com.geekymusketeers.uncrack.util.UtilsKt.getCommunicationAccounts
import com.geekymusketeers.uncrack.util.UtilsKt.getCommunitiesAccounts
import com.geekymusketeers.uncrack.util.UtilsKt.getCrowdSourcingAccounts
import com.geekymusketeers.uncrack.util.UtilsKt.getPortfolioAccounts
import com.geekymusketeers.uncrack.util.UtilsKt.getSocialAccounts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelectionScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "Social",
                style = medium20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val socialAccountMap = getSocialAccounts()

                items(socialAccountMap.size) { index ->
                    val entry = socialAccountMap.entries.elementAt(index)
                    val accountText = entry.key
                    val iconId = entry.value

                    AccountCard(
                        icon = iconId,
                        text = accountText.text,
                    ) {}

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Crowdsourcing",
                style = medium20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val crowdSourcingAccountMap = getCrowdSourcingAccounts()

                items(crowdSourcingAccountMap.size) { index ->
                    val entry = crowdSourcingAccountMap.entries.elementAt(index)
                    val accountText = entry.key
                    val iconId = entry.value

                    AccountCard(
                        icon = iconId,
                        text = accountText.text,
                    ) {}

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Communication",
                style = medium20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val crowdSourcingAccountMap = getCommunicationAccounts()

                items(crowdSourcingAccountMap.size) { index ->
                    val entry = crowdSourcingAccountMap.entries.elementAt(index)
                    val accountText = entry.key
                    val iconId = entry.value

                    AccountCard(
                        icon = iconId,
                        text = accountText.text,
                    ) {}

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Portfolio",
                style = medium20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val crowdSourcingAccountMap = getPortfolioAccounts()

                items(crowdSourcingAccountMap.size) { index ->
                    val entry = crowdSourcingAccountMap.entries.elementAt(index)
                    val accountText = entry.key
                    val iconId = entry.value

                    AccountCard(
                        icon = iconId,
                        text = accountText.text,
                    ) {}

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Communities",
                style = medium20.copy(Color.Black)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val crowdSourcingAccountMap = getCommunitiesAccounts()

                items(crowdSourcingAccountMap.size) { index ->
                    val entry = crowdSourcingAccountMap.entries.elementAt(index)
                    val accountText = entry.key
                    val iconId = entry.value

                    AccountCard(
                        icon = iconId,
                        text = accountText.text,
                    ) {}

                }
            }
        }
    }
}