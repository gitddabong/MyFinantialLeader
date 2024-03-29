package com.example.myfinancialleader.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.example.myfinancialleader.calendar.ui.theme.CalendarViewPager
import com.example.myfinancialleader.calendar.ui.theme.MyFinancialLeaderTheme
import com.example.myfinancialleader.calendar.ui.theme.expense_list.BottomExpenseListView
import kotlinx.coroutines.CoroutineScope

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
