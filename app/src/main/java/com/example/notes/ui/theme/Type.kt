package com.example.notes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.notes.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val nunitoFontFamily = GoogleFont("Nunito")

val fontFamily = FontFamily(
    Font(googleFont = nunitoFontFamily, fontProvider = provider)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
     //Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 43.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        color = LightGray
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ), bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 35.sp,
        letterSpacing = 0.sp,
        color = Color.White,
    ), displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 23.sp,
        letterSpacing = 0.sp,
        color = Color.White,
    ), displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        color = LightGray3,
    )/*,
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)