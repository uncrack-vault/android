package com.geekymusketeers.uncrack.presentation.intro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.components.OnboardingComponent
import com.geekymusketeers.uncrack.presentation.auth.login.LoginScreens
import com.geekymusketeers.uncrack.presentation.intro.model.OnBoardingItem
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryDark
import com.geekymusketeers.uncrack.ui.theme.UnCrackTheme
import com.geekymusketeers.uncrack.ui.theme.navigationTopBarHeight
import com.geekymusketeers.uncrack.ui.theme.normal16
import com.geekymusketeers.uncrack.util.UtilsKt.findActivity
import com.geekymusketeers.uncrack.util.onClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingScreen : ComponentActivity() {

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
                OnboardingContent(this@OnboardingScreen)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingContent(activity: Activity, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val pages = OnBoardingItem.onboardingScreenItems(context)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val buttonsState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("Next")
                1 -> listOf("Next")
                2 -> listOf("Login")
                else -> listOf("")
            }
        }
    }

    Column(
        modifier = modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = navigationTopBarHeight,
            bottom = bottomPadding
        ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        if (pagerState.currentPage != pages.lastIndex) {
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .onClick {
                        context
                            .findActivity()
                            ?.apply {
                                val loginIntent = Intent(activity, LoginScreens::class.java)
                                startActivity(loginIntent)
                                finish()
                            }
                    }
                    .align(Alignment.End),
                text = stringResource(R.string.skip),
                style = normal16.copy(OnSurfaceLight)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(state = pagerState) { index ->
            OnboardingComponent(item = pages[index])
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            PageIndicator(
                modifier = Modifier.fillMaxWidth(),
                pagesSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == pages.lastIndex) {
                            context
                                .findActivity()
                                ?.apply {
                                    val loginIntent = Intent(activity, LoginScreens::class.java)
                                    startActivity(loginIntent)
                                    finish()
                                }
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryDark
                ),
            ) {
                Text(
                    text = buttonsState.value[0],
                    color = Color.White,
                    fontFamily = DMSansFontFamily,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun PageIndicator(
    pagesSize: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = PrimaryDark,
    unselectedColor: Color = OnSurfaceVariantLight
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
