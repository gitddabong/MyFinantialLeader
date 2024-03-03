package com.example.myfinancialleader.calendar.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalendarView(dataList: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 7),
        contentPadding = PaddingValues(0.dp), // 각 아이템 사이의 패딩을 0으로 설정하여 간격 없음을 보장합니다.
        verticalArrangement = Arrangement.spacedBy(0.dp), // 세로 간격을 0으로 설정합니다.
        horizontalArrangement = Arrangement.spacedBy(0.dp) // 가로 간격을 0으로 설정합니다.
    ) {
        itemsIndexed(dataList) {rowIndex, rowData ->
            CellItem(text = rowData)
        }
    }
}

@Composable
fun CellItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$text")
    }
}
