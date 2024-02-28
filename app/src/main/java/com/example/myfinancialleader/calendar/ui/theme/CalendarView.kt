package com.example.myfinancialleader.calendar.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalendarView(matrix: List<List<Int>>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(matrix) { rowIndex, rowData ->
            Row(Modifier.padding(8.dp)) {
                rowData.forEachIndexed { colIndex, item ->
                    CellItem(item)
                }
            }
        }
    }
}

@Composable
fun CellItem(number: Int) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp, 40.dp)
            .border(1.dp, Color.Gray)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$number")
    }
}
