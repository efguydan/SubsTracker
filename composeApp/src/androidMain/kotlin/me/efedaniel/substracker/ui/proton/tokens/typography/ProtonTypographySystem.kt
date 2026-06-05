package me.efedaniel.substracker.ui.proton.tokens.typography

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import me.efedaniel.substracker.R

private val googleFontProvider =
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs,
    )

private val manropeFamily =
    FontFamily(
        Font(GoogleFont("Manrope"), googleFontProvider, FontWeight.Normal),
        Font(GoogleFont("Manrope"), googleFontProvider, FontWeight.SemiBold),
    )

private val interFamily =
    FontFamily(
        Font(GoogleFont("Inter"), googleFontProvider, FontWeight.Normal),
        Font(GoogleFont("Inter"), googleFontProvider, FontWeight.Medium),
        Font(GoogleFont("Inter"), googleFontProvider, FontWeight.SemiBold),
    )

val ProtonTypographySystem =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = manropeFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 56.sp,
                lineHeight = 64.sp,
                letterSpacing = (-0.02).em,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = manropeFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = (-0.02).em,
            ),
        titleLarge =
            TextStyle(
                fontFamily = manropeFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = (-0.02).em,
            ),
        titleMedium =
            TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = interFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.1.em,
            ),
    )
