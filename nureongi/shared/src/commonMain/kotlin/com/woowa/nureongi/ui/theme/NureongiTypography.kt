package com.woowa.nureongi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object NureongiTypography {
    val SectionHeader = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    val ItemTitle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    val ItemDescription = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal
    )
    val GuidanceMessageStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
    )
    val GuidanceInstructionStyle = TextStyle(
        fontSize = 36.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.ExtraBold,
    )
    val StatValueStyle = TextStyle(
        fontSize = 32.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.ExtraBold,
    )
    val StatLabelStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

internal val nureongiMaterialTypography = Typography(
    titleMedium = NureongiTypography.SectionHeader,
    bodyLarge = NureongiTypography.ItemTitle,
    bodyMedium = NureongiTypography.ItemDescription,
)
