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
import com.example.myfinancialleader.calendar.ui.theme.CalendarView
import com.example.myfinancialleader.calendar.ui.theme.MyFinancialLeaderTheme
import java.time.DayOfWeek
import java.time.LocalDate

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
                    showCalendarView()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun showCalendarView() {
//    CalendarView(getCalendarData(getDummyDate()))
//    CalendarView(getCalendarData(CalendarDate(2023,12,5)))
    CalendarView(getCalendarData(CalendarDate(2024,9,16)))
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyFinancialLeaderTheme {
        Greeting2("Android")
    }
}