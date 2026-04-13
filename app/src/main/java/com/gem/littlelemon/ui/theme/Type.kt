package com.gem.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gem.littlelemon.R

// Font families from the Little Lemon style guide.
// The .ttf files live in res/font/.
val MarkaziText = FontFamily(
    Font(R.font.markazi_text_regular, FontWeight.Normal)
)

val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

val Typography = Typography(
    // Restaurant name in hero section
    displayLarge = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize   = 64.sp
    ),
    // Section titles
    headlineLarge = TextStyle(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize   = 40.sp
    ),
    // Sub-titles (city, category buttons)
    titleMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize   = 18.sp
    ),
    // Body text, descriptions
    bodyLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp
    ),
    // Prices, labels
    bodyMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize   = 14.sp
    )
)
