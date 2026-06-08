package com.woowa.nureongi.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * docs/ui 스크린샷 기준 컬러 토큰.
 * 다크 배경 + 옐로우 강조색 조합으로, 명도 대비를 우선해 정의한다.
 */
object NureongiColors {
    val Background = Color(0xFF121212)
    val Surface = Color(0xFF1E1E1E)

    val Accent = Color(0xFFFFC400)
    val OnAccent = Color(0xFF000000)

    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFA0A0A0)

    val Disabled = Color(0xFF2C2C2C)
    val OnDisabled = Color(0xFF6E6E6E)

    /** 보조 CTA(예: "새 목적지 안내")처럼 강조색 대신 쓰는 화이트 컨테이너 색상. */
    val NeutralSurface = Color(0xFFFFFFFF)
}
