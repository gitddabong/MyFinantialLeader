package com.example.myfinancialleader

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.myfinancialleader.home.HomeActivity
import com.example.myfinancialleader.home.expense_addition.ExpenseAdditionNavController
import com.example.myfinancialleader.ui.theme.MyFinancialLeaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFinancialLeaderTheme {
                // A surface container using the 'background' color from the theme
                Button(
                    onClick = {
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    }
                ) {
                    Text(text = "액티비티 이동")
                }

                ExpenseAdditionNavController()

            }
        }
    }
}