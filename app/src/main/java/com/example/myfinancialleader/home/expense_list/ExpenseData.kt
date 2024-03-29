package com.example.myfinancialleader.home.expense_list

data class ExpenseData(
    val expenseType: ExpenseType,
    val category: String, // 임시로 String으로 작성하나, 객체 형태로 수정해야 함
    val detail: String,
    val amount: Int
)

enum class ExpenseType {
    EXPENSE,
    INCOME
}