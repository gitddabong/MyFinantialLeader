package com.example.myfinancialleader.calendar.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfinancialleader.R
import com.example.myfinancialleader.calendar.CalendarDate
import com.example.myfinancialleader.calendar.getCalendarData
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val LIMIT_CALENDAR_SIZE = Int.MAX_VALUE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarViewPager() {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = createCalendarPagerState()
        val (calendarTitle, calendarTable) = createRefs()

        CalendarTitleLayout(
            modifier = Modifier
                .constrainAs(calendarTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            pagerState = pagerState
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(calendarTable) { top.linkTo(calendarTitle.bottom, margin = 5.dp) },
            state = pagerState,
        ) { page ->
            val targetDate = getTargetDate(
                pagerState = pagerState,
                baseDate = LocalDate.now()
            )

            CalendarView(
                getCalendarData(targetDate),
                pagerState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarTitleLayout(modifier: Modifier, pagerState: PagerState) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (moveLeft, moveRight, title) = createRefs()

        val coroutineScope = rememberCoroutineScope()

        Button(
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(moveLeft) {
                    end.linkTo(title.start)
                },
            onClick = {
                val prevPage = (pagerState.currentPage - 1).coerceAtLeast(0)
                coroutineScope.launch { pagerState.animateScrollToPage(prevPage, 0f) }
            },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_triangle_left),
                    contentDescription = "left"
                )
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "${getTargetDate(LocalDate.now(), pagerState).month}",
            style = TextStyle(
                fontSize = 30.sp
            )
        )

        Button(
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(moveRight) {
                    start.linkTo(title.end)
                },
            onClick = {
                val nextPage = (pagerState.currentPage + 1).coerceAtLeast(0)
                coroutineScope.launch { pagerState.animateScrollToPage(nextPage, 0f) }
            },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_triangle_right),
                    contentDescription = "right"
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(dataList: List<String>, pagerState: PagerState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 7),
        contentPadding = PaddingValues(0.dp), // 각 아이템 사이의 패딩을 0으로 설정하여 간격 없음을 보장합니다.
        verticalArrangement = Arrangement.spacedBy(0.dp), // 세로 간격을 0으로 설정합니다.
        horizontalArrangement = Arrangement.spacedBy(0.dp), // 가로 간격을 0으로 설정합니다.
    ) {
        itemsIndexed(dataList) {rowIndex, rowData ->
            val isFocused = rowData.contains("${getTargetDate(LocalDate.now(), pagerState).month}월")
            if (isFocused) {
                CellItem(text = rowData)
            } else {
                BlankItem()
            }
        }
    }
}

@Composable
fun CellItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$text")
    }
}

@Composable
fun BlankItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .background(Color.Gray),
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun getTargetDate(baseDate: LocalDate, pagerState: PagerState): CalendarDate {
    val currentPage = pagerState.currentPage
    val targetDate = baseDate.plusMonths((currentPage - pagerState.initialPage).toLong())

    return CalendarDate(
        targetDate.year,
        targetDate.monthValue,
        targetDate.dayOfMonth
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun createCalendarPagerState(): PagerState {
    return rememberPagerState(
        initialPage = LIMIT_CALENDAR_SIZE / 2,
        initialPageOffsetFraction = 0f,
    ) {
        LIMIT_CALENDAR_SIZE
    }
}
