package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.geekymusketeers.uncrack.R

@Composable
fun ProfileContainer(
    modifier: Modifier = Modifier,
    onEditButtonClick: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Image(
            painter = painterResource(id = R.drawable.no_user_profile_picture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Button(
            modifier = Modifier.size(25.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            contentPadding = PaddingValues(0.dp),
            onClick = { onEditButtonClick() }
        ) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = null, tint = Color.White)
        }
    }
}