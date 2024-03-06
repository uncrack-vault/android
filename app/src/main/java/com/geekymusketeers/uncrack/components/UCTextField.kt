package com.geekymusketeers.uncrack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekymusketeers.uncrack.ui.theme.DMSansFontFamily
import com.geekymusketeers.uncrack.ui.theme.OnPrimaryContainerLight
import com.geekymusketeers.uncrack.ui.theme.OnSurfaceLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceTintLight
import com.geekymusketeers.uncrack.ui.theme.SurfaceVariantLight
import com.geekymusketeers.uncrack.ui.theme.normal16

@Composable
fun UCTextField(
    value: String,
    modifier: Modifier = Modifier,
    headerText: String = "",
    hintText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    textColor: Color = OnSurfaceLight,
    backgroundColor: Color = SurfaceVariantLight,
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(10.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        if (headerText.isNotEmpty()) {
            TextHeader(text = headerText)
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextEditField(
            hintText = hintText,
            value = value,
            keyboardType = keyboardType,
            keyboardActions = keyboardActions,
            textColor = textColor,
            backgroundColor = backgroundColor,
            textStyle = textStyle,
            enabled = enabled,
            shape = shape,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            leadingIcon = leadingIcon,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        ) { onValueChange(it) } // Use String instead of MutableState<String>
    }
}

@Composable
fun TextHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = DMSansFontFamily,
            fontWeight = FontWeight(500),
            color = OnPrimaryContainerLight,
        )
    )
}

@Composable
fun TextEditField(
    shape: Shape,
    modifier: Modifier = Modifier,
    hintText: String = "",
    value: String = "",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = OnSurfaceLight,
    backgroundColor: Color = SurfaceVariantLight,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        keyboardOptions = keyboardOptions,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(textColor),
        placeholder = {
            Text(
                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                text = hintText,
                color = SurfaceTintLight,
                fontFamily = DMSansFontFamily
            )
        },
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        shape = shape,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation
    )
}