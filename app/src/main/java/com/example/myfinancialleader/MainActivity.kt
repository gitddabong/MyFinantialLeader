package com.example.myfinancialleader

import android.content.Intent
import android.icu.util.LocaleData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfinancialleader.calendar.HomeActivity
import com.example.myfinancialleader.ui.theme.MyFinancialLeaderTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFinancialLeaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                    ) {
                        Text(text = "액티비티 이동")
                    }
                    
                    Text(
                        text = "Hello ${LocalDate.now()}!",
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello ${LocalDate.now()}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFinancialLeaderTheme {
        Greeting("Android")
    }
}