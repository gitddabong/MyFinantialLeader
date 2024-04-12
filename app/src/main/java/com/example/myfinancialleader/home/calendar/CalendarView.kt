package com.example.myfinancialleader.home.calendar

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfinancialleader.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.abs

private const val LIMIT_CALENDAR_SIZE = Int.MAX_VALUE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarViewPager(modifier: Modifier) {
    ConstraintLayout (
        modifier = modifier.fillMaxSize()
    ) {
        val pagerState = createCalendarPagerState()
        val (calendarTitle, dayOfWeekList, calendarTable) = createRefs()

        CalendarTitleLayout(
            modifier = Modifier
                .constrainAs(calendarTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            pagerState = pagerState
        )

        DayOfWeekLayout(
            modifier = Modifier
                .constrainAs(dayOfWeekList) {
                    top.linkTo(calendarTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(calendarTable) { top.linkTo(dayOfWeekList.bottom, margin = 5.dp) },
            state = pagerState,
            pageSpacing = 15.dp,
            beyondBoundsPageCount = 2
        ) { index ->
            @Suppress("NAME_SHADOWING")
            val index by rememberUpdatedState(newValue = index)
            val authorizedPage = {
                abs(pagerState.settledPage - index) <= 2
            }
            val authorizedTiming by produceState(initialValue = false) {
                while (pagerState.isScrollInProgress) delay(50)
                if (abs(pagerState.settledPage - index) > 0) {
                    delay(500)
                    while (pagerState.isScrollInProgress) delay(50)
                }
                value = true
            }
            // Conditions to show content
            val showContent by remember {
                derivedStateOf {
                    authorizedPage() && authorizedTiming
                }
            }

            if (showContent) {
                val targetDate = getTargetDate(
                    currentIndex = index,
                    pagerState = pagerState,
                    baseDate = LocalDate.now()
                )

                CalendarView(
                    dataList = getCalendarData(targetDate),
                    pagerState = pagerState,
                    currentIndex = index
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
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

@Composable
fun DayOfWeekLayout(modifier: Modifier) {
    val dayOfWeekItems = listOf(
        "SUN", "MON", "TUS", "WED", "THU", "FRI", "SAT"
    )

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 7),
        contentPadding = PaddingValues(0.dp), // 각 아이템 사이의 패딩을 0으로 설정하여 간격 없음을 보장합니다.
        verticalArrangement = Arrangement.spacedBy(0.dp), // 세로 간격을 0으로 설정합니다.
        horizontalArrangement = Arrangement.spacedBy(0.dp), // 가로 간격을 0으로 설정합니다.
    ) {
        itemsIndexed(dayOfWeekItems) { rowIndex, rowData ->
            CellItem(text = rowData)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(dataList: List<String>, pagerState: PagerState, currentIndex: Int = pagerState.currentPage) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 7),
        contentPadding = PaddingValues(0.dp), // 각 아이템 사이의 패딩을 0으로 설정하여 간격 없음을 보장합니다.
        verticalArrangement = Arrangement.spacedBy(0.dp), // 세로 간격을 0으로 설정합니다.
        horizontalArrangement = Arrangement.spacedBy(0.dp), // 가로 간격을 0으로 설정합니다.
    ) {
        itemsIndexed(dataList) {rowIndex, rowData ->
            val isFocused = rowData.contains("${getTargetDate(LocalDate.now(), pagerState, currentIndex).month}월")
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
private fun getTargetDate(baseDate: LocalDate, pagerState: PagerState, currentIndex: Int = pagerState.currentPage): CalendarDate {
    val targetDate = baseDate.plusMonths((currentIndex - pagerState.initialPage).toLong())

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
