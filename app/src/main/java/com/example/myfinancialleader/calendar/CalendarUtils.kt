package com.example.myfinancialleader.calendar

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * 2024-02-28 과 같은 ISO 8601 포맷으로 입력합니다.
 */
fun getCalendarData(targetDate: CalendarDate): List<List<String>> {
    // 연도, 월에 맞춰서 7의 배수의 캘린더 리스트 생성
    // 예를 들어 3월 1일이 목요일이라면, 그 전 데이터는 2월의 데이터로 채워야 함.
    // 3월 31일이 화요일이라면, 그 이후의 데이터는 4월의 데이터로 채워야 함.

    // 우선, 한 달의 데이터를 모두 만드는 것으로 시작.
    // 그 달이 아닌 이외의 칸은 모두 0으로 채우기

    // 첫 날의 요일 알아내기
    val gap = when (LocalDate.of(targetDate.year, targetDate.month, 1).dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
    }



    // 일별 데이터를 담을 일차원 배열
    val dataOfOnePage = MutableList(42) { " " }

    // 앞 달과 앞 달의 일수
    val lastMonth = if ((targetDate.month - 1) % 12 == 0) 12 else (targetDate.month - 1) % 12
    val getDaysInLastMonth = getDaysInMonths(targetDate.year)[lastMonth]

    for (i in 0..< gap) {
        dataOfOnePage[i] = "${lastMonth}월\n ${getDaysInLastMonth - gap + 1 + i}일"
    }

    // 이번 달 일수
    val thisMonth = targetDate.month
    val getDaysInThisMonth = getDaysInMonths(targetDate.year)[targetDate.month]
    var count = 1
    for (i in gap..<gap + getDaysInThisMonth) {
        dataOfOnePage[i] = "${thisMonth}월\n ${count}일"
        count++
    }

    // 다음 달 일수
    val nextMonth = if ((targetDate.month + 1) % 12 == 0) 12 else (targetDate.month + 1) % 12

    count = 1
    for (i in gap + getDaysInThisMonth ..< dataOfOnePage.size) {
        dataOfOnePage[i] = "${nextMonth}월\n ${count}일"
        count++
    }

    return splitArrayIntoChunks(dataOfOnePage)
}

fun splitArrayIntoChunks(list: List<String>): List<List<String>> {
    val chunkSize = 7
    val result = mutableListOf<List<String>>()

    for (i in list.indices step chunkSize) {
        val chunk = list.slice(i until minOf(i + chunkSize, list.size))
        result.add(chunk)
    }

    return result
}

fun getDaysInMonths(year: Int): List<Int> {
    // 각 월의 총 일 수 계산
    return listOf(
        0, 31, if (isLeapYear(year)) 29 else 28, // 1월
        31, 30, 31, 30, 31, 31, 30, 31, 30, 31 // 2월부터 12월까지
    )
}

fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

fun convertToCalendarDate(date: String): CalendarDate {
    val inputData = date.split("-")
    val year = inputData[0].toInt()
    val month = inputData[1].toInt()
    val day = inputData[2].toInt()

    return CalendarDate(year, month, day)
}

fun convertToLocalDate(calendarDate: CalendarDate): LocalDate {
    return LocalDate.of(calendarDate.year, calendarDate.month, calendarDate.day)
}

fun getDummyDate(): CalendarDate {
    return convertToCalendarDate(LocalDate.now().toString())
}

data class CalendarDate (
    val year: Int,
    val month: Int,
    val day: Int
)