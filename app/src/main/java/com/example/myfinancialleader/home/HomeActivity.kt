package com.example.myfinancialleader.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
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
                        val (calendarViewPager, expenseListView) = createRefs()

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


                    }
                }
            }
        }
    }
}
