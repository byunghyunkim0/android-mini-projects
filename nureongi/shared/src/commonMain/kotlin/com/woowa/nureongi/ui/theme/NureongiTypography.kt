package com.woowa.nureongi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * docs/ui 스크린샷 기준 타이포 토큰.
 * 섹션 헤더 / 리스트 아이템 제목·설명 3단계로 구성한다.
 */
object NureongiTypography {
    val SectionHeader = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    val ItemTitle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    val ItemDescription = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal)
}

internal val nureongiMaterialTypography = Typography(
    titleMedium = NureongiTypography.SectionHeader,
    bodyLarge = NureongiTypography.ItemTitle,
    bodyMedium = NureongiTypography.ItemDescription,
)
