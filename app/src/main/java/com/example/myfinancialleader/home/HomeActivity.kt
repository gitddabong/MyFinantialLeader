package com.example.myfinancialleader.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfinancialleader.expense_addition.ExpenseAdditionActivity
import com.example.myfinancialleader.home.calendar.CalendarViewPager
import com.example.myfinancialleader.home.expense_list.BottomExpenseListView
import com.example.myfinancialleader.ui.theme.MyFinancialLeaderTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFinancialLeaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConstraintLayout {
                        val (calendarViewPager, expenseListView, ExpenseAdditionButton) = createRefs()

                        CalendarViewPager(modifier = Modifier
                            .constrainAs(calendarViewPager) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )

                        BottomExpenseListView(modifier = Modifier
                            .constrainAs(expenseListView) {
                                top.linkTo(calendarViewPager.bottom)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )

                        FloatingAdditionButton(modifier = Modifier
                            .constrainAs(ExpenseAdditionButton) {
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .zIndex(1.0f),
                            onClick = {
                                startActivity(Intent(this@HomeActivity, ExpenseAdditionActivity::class.java))
                            }
                        )
                    }
                }
            }
        }
    }
}
