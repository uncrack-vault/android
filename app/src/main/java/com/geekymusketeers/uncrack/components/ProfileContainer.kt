package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.sharedViewModel.UserViewModel
import com.geekymusketeers.uncrack.ui.theme.BackgroundLight
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.PrimaryLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceLight
import com.geekymusketeers.uncrack.ui.theme.bold22
import com.geekymusketeers.uncrack.ui.theme.bold24

@Composable
fun ProfileContainer(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {

    var initials by remember { mutableStateOf("") }
    val userData = userViewModel.state.value
    initials = getInitials(userData.name)


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(PrimaryLight)
    ) {
        if (initials.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.no_user_profile_picture),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = initials,
                style = bold24.copy(SurfaceLight)
            )
        }
    }
}

fun getInitials(name: String): String {
    val nameParts = name.trim().split(' ')
    var initials = nameParts[0].first().uppercaseChar().toString()
    if (nameParts.size > 1) {
        initials += nameParts[1].first()
    } else if (nameParts[0].length > 1) {
        initials += nameParts[0][1]
    }
    return initials
}