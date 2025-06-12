package com.aritradas.uncrack.presentation.intro

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aritradas.uncrack.R
import com.aritradas.uncrack.components.OnboardingComponent
import com.aritradas.uncrack.components.UCButton
import com.aritradas.uncrack.components.UCStrokeButton
import com.aritradas.uncrack.navigation.Screen
import com.aritradas.uncrack.presentation.intro.model.OnBoardingItem
import com.aritradas.uncrack.ui.theme.OnSurfaceLight
import com.aritradas.uncrack.ui.theme.OnSurfaceVariantLight
import com.aritradas.uncrack.ui.theme.PrimaryDark
import com.aritradas.uncrack.ui.theme.medium18


@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val pages = OnBoardingItem.onboardingScreenItems()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = bottomPadding
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        if (pagerState.currentPage != pages.lastIndex) {
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate(Screen.LoginScreen.name)
                    },
                text = stringResource(R.string.skip),
                style = medium18.copy(MaterialTheme.colorScheme.onSurface)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(8f)
        ) { index ->
            OnboardingComponent(item = pages[index])
        }

        PageIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            pagesSize = pages.size,
            selectedPage = pagerState.currentPage
        )

        Spacer(modifier = Modifier.height(32.dp))

        UCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.get_started),
            onClick = {
                navController.navigate(Screen.LoginScreen.name)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        UCStrokeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_an_account),
            onClick = {
                navController.navigate(Screen.SignUpScreen.name)
            }
        )
    }
}

@Composable
fun PageIndicator(
    pagesSize: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}
