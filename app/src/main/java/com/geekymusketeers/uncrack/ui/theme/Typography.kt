package com.geekymusketeers.uncrack.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val fontFamily = DMSansFontFamily

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontSize = 11.sp
    )
)

fun generateTextStyle(weight: FontWeight, size: Int): TextStyle {
    return TextStyle(
        fontSize = size.sp,
        fontFamily = fontFamily,
        fontWeight = weight
    )
}

val normal11 = generateTextStyle(FontWeight.Normal, 11)
val normal12 = generateTextStyle(FontWeight.Normal, 12)
val normal14 = generateTextStyle(FontWeight.Normal, 14)
val normal16 = generateTextStyle(FontWeight.Normal, 16)
val normal18 = generateTextStyle(FontWeight.Normal, 18)
val normal20 = generateTextStyle(FontWeight.Normal, 20)
val normal22 = generateTextStyle(FontWeight.Normal, 22)
val normal24 = generateTextStyle(FontWeight.Normal, 24)
val normal26 = generateTextStyle(FontWeight.Normal, 26)
val normal28 = generateTextStyle(FontWeight.Normal, 28)
val normal30 = generateTextStyle(FontWeight.Normal, 30)
val normal32 = generateTextStyle(FontWeight.Normal, 32)
val normal34 = generateTextStyle(FontWeight.Normal, 34)
val normal36 = generateTextStyle(FontWeight.Normal, 36)
val normal48 = generateTextStyle(FontWeight.Normal, 48)
val normal57 = generateTextStyle(FontWeight.Normal, 57)


val thin11 = generateTextStyle(FontWeight.Thin, 11)
val thin12 = generateTextStyle(FontWeight.Thin, 12)
val thin14 = generateTextStyle(FontWeight.Thin, 14)
val thin16 = generateTextStyle(FontWeight.Thin, 16)
val thin18 = generateTextStyle(FontWeight.Thin, 18)
val thin20 = generateTextStyle(FontWeight.Thin, 20)
val thin22 = generateTextStyle(FontWeight.Thin, 22)
val thin24 = generateTextStyle(FontWeight.Thin, 24)
val thin26 = generateTextStyle(FontWeight.Thin, 26)
val thin28 = generateTextStyle(FontWeight.Thin, 28)
val thin30 = generateTextStyle(FontWeight.Thin, 30)
val thin32 = generateTextStyle(FontWeight.Thin, 32)
val thin34 = generateTextStyle(FontWeight.Thin, 34)
val thin36 = generateTextStyle(FontWeight.Thin, 36)
val thin48 = generateTextStyle(FontWeight.Thin, 48)
val thin57 = generateTextStyle(FontWeight.Thin, 57)

val light11 = generateTextStyle(FontWeight.Light, 11)
val light12 = generateTextStyle(FontWeight.Light, 12)
val light14 = generateTextStyle(FontWeight.Light, 14)
val light16 = generateTextStyle(FontWeight.Light, 16)
val light18 = generateTextStyle(FontWeight.Light, 18)
val light20 = generateTextStyle(FontWeight.Light, 20)
val light22 = generateTextStyle(FontWeight.Light, 22)
val light24 = generateTextStyle(FontWeight.Light, 24)
val light26 = generateTextStyle(FontWeight.Light, 26)
val light28 = generateTextStyle(FontWeight.Light, 28)
val light30 = generateTextStyle(FontWeight.Light, 30)
val light32 = generateTextStyle(FontWeight.Light, 32)
val light34 = generateTextStyle(FontWeight.Light, 34)
val light36 = generateTextStyle(FontWeight.Light, 36)
val light48 = generateTextStyle(FontWeight.Light, 48)
val light57 = generateTextStyle(FontWeight.Light, 57)

val extraLight11 = generateTextStyle(FontWeight.ExtraLight, 11)
val extraLight12 = generateTextStyle(FontWeight.ExtraLight, 12)
val extraLight14 = generateTextStyle(FontWeight.ExtraLight, 14)
val extraLight16 = generateTextStyle(FontWeight.ExtraLight, 16)
val extraLight18 = generateTextStyle(FontWeight.ExtraLight, 18)
val extraLight20 = generateTextStyle(FontWeight.ExtraLight, 20)
val extraLight22 = generateTextStyle(FontWeight.ExtraLight, 22)
val extraLight24 = generateTextStyle(FontWeight.ExtraLight, 24)
val extraLight26 = generateTextStyle(FontWeight.ExtraLight, 26)
val extraLight28 = generateTextStyle(FontWeight.ExtraLight, 28)
val extraLight30 = generateTextStyle(FontWeight.ExtraLight, 30)
val extraLight32 = generateTextStyle(FontWeight.ExtraLight, 32)
val extraLight34 = generateTextStyle(FontWeight.ExtraLight, 34)
val extraLight36 = generateTextStyle(FontWeight.ExtraLight, 36)
val extraLight48 = generateTextStyle(FontWeight.ExtraLight, 48)
val extraLight57 = generateTextStyle(FontWeight.ExtraLight, 57)

val medium11 = generateTextStyle(FontWeight.Medium, 11)
val medium12 = generateTextStyle(FontWeight.Medium, 12)
val medium14 = generateTextStyle(FontWeight.Medium, 14)
val medium16 = generateTextStyle(FontWeight.Medium, 16)
val medium18 = generateTextStyle(FontWeight.Medium, 18)
val medium20 = generateTextStyle(FontWeight.Medium, 20)
val medium22 = generateTextStyle(FontWeight.Medium, 22)
val medium24 = generateTextStyle(FontWeight.Medium, 24)
val medium26 = generateTextStyle(FontWeight.Medium, 26)
val medium28 = generateTextStyle(FontWeight.Medium, 28)
val medium30 = generateTextStyle(FontWeight.Medium, 30)
val medium32 = generateTextStyle(FontWeight.Medium, 32)
val medium34 = generateTextStyle(FontWeight.Medium, 34)
val medium36 = generateTextStyle(FontWeight.Medium, 36)
val medium48 = generateTextStyle(FontWeight.Medium, 48)
val medium57 = generateTextStyle(FontWeight.Medium, 57)

val semiBold11 = generateTextStyle(FontWeight.SemiBold, 11)
val semiBold12 = generateTextStyle(FontWeight.SemiBold, 12)
val semiBold14 = generateTextStyle(FontWeight.SemiBold, 14)
val semiBold16 = generateTextStyle(FontWeight.SemiBold, 16)
val semiBold18 = generateTextStyle(FontWeight.SemiBold, 18)
val semiBold20 = generateTextStyle(FontWeight.SemiBold, 20)
val semiBold22 = generateTextStyle(FontWeight.SemiBold, 22)
val semiBold24 = generateTextStyle(FontWeight.SemiBold, 24)
val semiBold26 = generateTextStyle(FontWeight.SemiBold, 26)
val semiBold28 = generateTextStyle(FontWeight.SemiBold, 28)
val semiBold30 = generateTextStyle(FontWeight.SemiBold, 30)
val semiBold32 = generateTextStyle(FontWeight.SemiBold, 32)
val semiBold34 = generateTextStyle(FontWeight.SemiBold, 34)
val semiBold36 = generateTextStyle(FontWeight.SemiBold, 36)
val semiBold48 = generateTextStyle(FontWeight.SemiBold, 48)
val semiBold57 = generateTextStyle(FontWeight.SemiBold, 57)

val bold11 = generateTextStyle(FontWeight.Bold, 11)
val bold12 = generateTextStyle(FontWeight.Bold, 12)
val bold14 = generateTextStyle(FontWeight.Bold, 14)
val bold16 = generateTextStyle(FontWeight.Bold, 16)
val bold18 = generateTextStyle(FontWeight.Bold, 18)
val bold20 = generateTextStyle(FontWeight.Bold, 20)
val bold22 = generateTextStyle(FontWeight.Bold, 22)
val bold24 = generateTextStyle(FontWeight.Bold, 24)
val bold26 = generateTextStyle(FontWeight.Bold, 26)
val bold28 = generateTextStyle(FontWeight.Bold, 28)
val bold30 = generateTextStyle(FontWeight.Bold, 30)
val bold32 = generateTextStyle(FontWeight.Bold, 32)
val bold34 = generateTextStyle(FontWeight.Bold, 34)
val bold36 = generateTextStyle(FontWeight.Bold, 36)
val bold48 = generateTextStyle(FontWeight.Bold, 48)
val bold57 = generateTextStyle(FontWeight.Bold, 57)

val extraBold11 = generateTextStyle(FontWeight.ExtraBold, 11)
val extraBold12 = generateTextStyle(FontWeight.ExtraBold, 12)
val extraBold14 = generateTextStyle(FontWeight.ExtraBold, 14)
val extraBold16 = generateTextStyle(FontWeight.ExtraBold, 16)
val extraBold18 = generateTextStyle(FontWeight.ExtraBold, 18)
val extraBold20 = generateTextStyle(FontWeight.ExtraBold, 20)
val extraBold22 = generateTextStyle(FontWeight.ExtraBold, 22)
val extraBold24 = generateTextStyle(FontWeight.ExtraBold, 24)
val extraBold26 = generateTextStyle(FontWeight.ExtraBold, 26)
val extraBold28 = generateTextStyle(FontWeight.ExtraBold, 28)
val extraBold30 = generateTextStyle(FontWeight.ExtraBold, 30)
val extraBold32 = generateTextStyle(FontWeight.ExtraBold, 32)
val extraBold34 = generateTextStyle(FontWeight.ExtraBold, 34)
val extraBold36 = generateTextStyle(FontWeight.ExtraBold, 36)
val extraBold48 = generateTextStyle(FontWeight.ExtraBold, 48)
val extraBold57 = generateTextStyle(FontWeight.ExtraBold, 57)

val black11 = generateTextStyle(FontWeight.Black, 11)
val black12 = generateTextStyle(FontWeight.Black, 12)
val black14 = generateTextStyle(FontWeight.Black, 14)
val black16 = generateTextStyle(FontWeight.Black, 16)
val black18 = generateTextStyle(FontWeight.Black, 18)
val black20 = generateTextStyle(FontWeight.Black, 20)
val black22 = generateTextStyle(FontWeight.Black, 22)
val black24 = generateTextStyle(FontWeight.Black, 24)
val black26 = generateTextStyle(FontWeight.Black, 26)
val black28 = generateTextStyle(FontWeight.Black, 28)
val black30 = generateTextStyle(FontWeight.Black, 30)
val black32 = generateTextStyle(FontWeight.Black, 32)
val black34 = generateTextStyle(FontWeight.Black, 34)
val black36 = generateTextStyle(FontWeight.Black, 36)
val black48 = generateTextStyle(FontWeight.Black, 48)
val black57 = generateTextStyle(FontWeight.Black, 57)



@Composable
fun FontCard(family: String, size: String, style: TextStyle) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(),
        modifier = Modifier.padding(8.dp),
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = family, style = style)
            Text(text = size)
        }
    }
}

@Preview
@Composable
fun PreviewTypography() {
    UnCrackTheme {
        Surface {
            Row {
                Column {
                    FontCard("Display", "L", Typography.displayLarge)
                    FontCard("Display", "M", Typography.displayMedium)
                    FontCard("Display", "S", Typography.displaySmall)
                    FontCard("Headline", "L", Typography.headlineLarge)
                    FontCard("Headline", "M", Typography.headlineMedium)
                    FontCard("Headline", "S", Typography.headlineSmall)
                    FontCard("Title", "L", Typography.titleLarge)
                    FontCard("Title", "M", Typography.titleMedium)
                    FontCard("Title", "S", Typography.titleSmall)
                }
                Column {
                    FontCard("Body", "L", Typography.bodyLarge)
                    FontCard("Body", "M", Typography.bodyMedium)
                    FontCard("Body", "S", Typography.bodySmall)
                    FontCard("Label", "L", Typography.labelLarge)
                    FontCard("Label", "M", Typography.labelMedium)
                    FontCard("Label", "S", Typography.labelSmall)
                }
            }
        }
    }
}