package com.woowa.nureongi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.nureongi.ui.model.PlaceUiModel
import com.woowa.nureongi.ui.theme.NureongiColors
import com.woowa.nureongi.ui.theme.NureongiTheme
import com.woowa.nureongi.ui.theme.NureongiTypography

/**
 * 장소·목적지 한 건을 보여주는 리스트 카드.
 *
 * 비즈니스 로직(선택 처리, 데이터 조회 등)을 갖지 않고, 표시할 값([PlaceUiModel])과
 * 선택 여부·콜백만 파라미터로 받는다(상태 호이스팅). 선택 상태는 보더·아이콘 색상뿐
 * 아니라 스크린 리더의 "선택됨" 상태로도 함께 전달한다.
 *
 * @param place 표시할 장소 정보
 * @param selected 현재 선택된 항목인지 여부
 * @param onClick 항목을 선택했을 때 호출되는 콜백
 */
@Composable
fun PlaceListItem(
    place: PlaceUiModel,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (selected) NureongiColors.Accent else NureongiColors.Surface
    val iconBackground = if (selected) NureongiColors.Accent else NureongiColors.Surface
    val dotColor = if (selected) NureongiColors.OnAccent else NureongiColors.Accent

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NureongiColors.Surface)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Button,
            )
            .semantics {
                this.selected = selected
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrailleIcon(
            backgroundColor = iconBackground,
            dotColor = dotColor,
            modifier = Modifier.size(40.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = place.name,
                style = NureongiTypography.ItemTitle,
                color = NureongiColors.TextPrimary,
            )
            Text(
                text = place.location,
                style = NureongiTypography.ItemDescription,
                color = NureongiColors.TextSecondary,
            )
        }
    }
}

@Preview
@Composable
private fun PlaceListItemPreview() {
    NureongiTheme {
        Column(
            modifier = Modifier
                .background(NureongiColors.Background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            var selectedIndex by remember { mutableStateOf(1) }
            val places = listOf(
                PlaceUiModel("1번 출구", "지상 · 버스정류장 방면"),
                PlaceUiModel("2번 출구", "지상 · 광장 방면"),
                PlaceUiModel("화장실", "대합실 왼쪽"),
            )
            places.forEachIndexed { index, place ->
                PlaceListItem(
                    place = place,
                    selected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                )
            }
        }
    }
}
