package com.example.myfinancialleader.calendar.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfinancialleader.calendar.CalendarDate
import com.example.myfinancialleader.calendar.getCalendarData
import java.time.LocalDate

private const val LIMIT_CALENDAR_SIZE = Int.MAX_VALUE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarViewPager() {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ) {
        val (calendarTitle, calendarTable) = createRefs()
        val pagerState = rememberPagerState(
            initialPage = LIMIT_CALENDAR_SIZE / 2,
            initialPageOffsetFraction = 0f,
        ) {
            LIMIT_CALENDAR_SIZE
        }

        Text(
            modifier = Modifier.constrainAs(calendarTitle) { top.linkTo(parent.top, margin = 10.dp) },
            text = "${getTargetDate(pagerState, LocalDate.now()).month}"
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(calendarTable) { top.linkTo(calendarTitle.bottom, margin = 5.dp) },
            state = pagerState
        ) { page ->
            val targetDate = getTargetDate(
                pagerState = pagerState,
                baseDate = LocalDate.now()
            )

            CalendarView(
                getCalendarData(targetDate)
            )


//        CalendarView(getCalendarData(CalendarDate(2024, 9, 16)))
        }
    }
}

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

@OptIn(ExperimentalFoundationApi::class)
private fun getTargetDate(pagerState: PagerState, baseDate: LocalDate): CalendarDate {
    val currentPage = pagerState.currentPage
    val targetDate = baseDate.plusMonths((currentPage - pagerState.initialPage).toLong())

    return CalendarDate(
        targetDate.year,
        targetDate.monthValue,
        targetDate.dayOfMonth
    )
}