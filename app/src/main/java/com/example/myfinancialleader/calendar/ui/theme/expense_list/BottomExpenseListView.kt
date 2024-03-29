package com.example.myfinancialleader.calendar.ui.theme.expense_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun BottomExpenseListView(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().fillMaxHeight()
    ) {
        items(items = getDummyExpenseDataList()) { item ->
            ExpenseItem(item)
        }
    }
}

@Composable
fun ExpenseItem(expenseData: ExpenseData) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()
        .width(100.dp)
        .height(50.dp)
        .border(1.dp, Color.Black)
    ) {
        val (category, detail, amount) = createRefs()

        Box(modifier = Modifier
            .constrainAs(category) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            .border(1.dp, Color.Gray)
            .background(Color.Gray)
        ) {
            Text(text = expenseData.category)
        }

        Text(modifier = Modifier
            .constrainAs(detail) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(category.end)
            },
            text = expenseData.detail,
        )

        Text(modifier = Modifier
            .constrainAs(amount) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            text = expenseData.amount.toString(),
            color = if (expenseData.expenseType == ExpenseType.EXPENSE) Color.Red else Color.Green
        )
    }
}

fun getDummyExpenseDataList(): List<ExpenseData> {
    return listOf(
        ExpenseData(ExpenseType.EXPENSE, "쿠팡 주문", "양배추", 19600),
        ExpenseData(ExpenseType.EXPENSE, "공과금", "전기 요금", 8700),
        ExpenseData(ExpenseType.INCOME, "캐시백", "교통카드 캐시백", 15200),
        ExpenseData(ExpenseType.EXPENSE, "편의점", "술안주", 5300),
        ExpenseData(ExpenseType.EXPENSE, "쿠팡 주문", "양배추", 19600),
        ExpenseData(ExpenseType.EXPENSE, "공과금", "전기 요금", 8700),
        ExpenseData(ExpenseType.INCOME, "캐시백", "교통카드 캐시백", 15200),
        ExpenseData(ExpenseType.EXPENSE, "편의점", "술안주", 5300),
        ExpenseData(ExpenseType.EXPENSE, "쿠팡 주문", "양배추", 19600),
        ExpenseData(ExpenseType.EXPENSE, "공과금", "전기 요금", 8700),
        ExpenseData(ExpenseType.INCOME, "캐시백", "교통카드 캐시백", 15200),
        ExpenseData(ExpenseType.EXPENSE, "편의점", "술안주", 5300),ExpenseData(ExpenseType.EXPENSE, "쿠팡 주문", "양배추", 19600),
        ExpenseData(ExpenseType.EXPENSE, "공과금", "전기 요금", 8700),
        ExpenseData(ExpenseType.INCOME, "캐시백", "교통카드 캐시백", 15200),
        ExpenseData(ExpenseType.EXPENSE, "편의점", "술안주", 5300),ExpenseData(ExpenseType.EXPENSE, "쿠팡 주문", "양배추", 19600),
        ExpenseData(ExpenseType.EXPENSE, "공과금", "전기 요금", 8700),
        ExpenseData(ExpenseType.INCOME, "캐시백", "교통카드 캐시백", 15200),
        ExpenseData(ExpenseType.EXPENSE, "편의점", "술안주", 5300)
    )
}