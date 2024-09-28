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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.EmptyState
import com.geekymusketeers.uncrack.components.VaultCard
import com.geekymusketeers.uncrack.sharedViewModel.UserViewModel
import com.geekymusketeers.uncrack.presentation.vault.viewmodel.VaultViewModel
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.medium24
import com.geekymusketeers.uncrack.ui.theme.normal16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultScreen(
    onFabClicked: () -> Unit,
    vaultViewModel: VaultViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
    navigateToViewPasswordScreen: (id: Int) -> Unit
) {
    val accounts by vaultViewModel.filteredAccounts.collectAsState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val user by userViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        vaultViewModel.getAccounts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(vertical = 90.dp),
                onClick = { onFabClicked() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Credentials"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(SurfaceVariantLight)
                .padding(16.dp)
        ) {
            Text(
                text = "Hello, ${user.name}",
                style = medium24.copy(Color.Black)
            )

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    vaultViewModel.searchAccount(it)
                },
                onSearch = { vaultViewModel.searchAccount(it) },
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(
                        text = "Search here",
                        style = normal16.copy(OnSurfaceVariantLight),
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = PrimaryContainerLight
                ),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                searchQuery = ""
                                vaultViewModel.searchAccount("")
                            }
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            ) { }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (accounts.isNotEmpty()) {
                    items(accounts) { accountModel ->
                        VaultCard(
                            accountModel = accountModel,
                            onClick = {
                                navigateToViewPasswordScreen(accountModel.id)
                            }
                        )
                    }
                } else {
                    item {
                        EmptyState(
                            stateTitle = "Hey ${user.name}, \n currently there are no passwords saved",
                            image = R.drawable.vault_empty_state
                        )
                    }
                }
            }
        }
    }
}