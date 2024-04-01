package com.example.myfinancialleader.expense_addition

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinancialleader.R
import com.example.myfinancialleader.home.expense_list.ExpenseType

@Composable
fun ExpenseAdditionNavController(onBackPressed: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
            FirstScreen(
                navController = navController,
                onBackPressed = onBackPressed
            )
        }
        composable("second/{expenseType}/{amount}") { backStackEntry ->
            backStackEntry.arguments?.apply {
                val expenseType = getString("expenseType")
                val amount = getString("amount")

                if (expenseType != null && amount != null) {
                    SecondScreen(navController, expenseType, amount)
                }
            }
        }
        composable("third/{value}") { backStackEntry ->
//            ThirdScreen(
//                navController = navController,
//                value = backStackEntry.arguments?.getString("value") ?: ""
//            )
        }
    }
}

// 뒤로 가기 및 취소 버튼이 포함된 커스텀 컴포저블 레이아웃
@Composable
fun ExpenseAdditionLayout(onBackPressed: () -> Unit, content: @Composable ConstraintLayoutScope.() -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backButton, mainContents) = createRefs()

        Button(
            modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(80.dp)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(5.dp),
            onClick = {
                onBackPressed.invoke()
            }
        ) {
            Image (
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "ExpenseAdditionBackButton",
                contentScale = ContentScale.Fit
            )
        }

        ConstraintLayout(modifier = Modifier
            .constrainAs(mainContents) {
                top.linkTo(backButton.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            content()
        }
    }
}

@Composable
fun FirstScreen(navController: NavController, onBackPressed: () -> Unit) {
    ExpenseAdditionLayout(
        onBackPressed = onBackPressed
    ) {
        val (cashImage, amountTextField, incomeButton, expenseButton) = createRefs()
        var amount by remember {
            mutableStateOf("")
        }

        Image(
            modifier = Modifier
                .size(70.dp)
                .padding(10.dp)
                .constrainAs(cashImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(amountTextField.start)
                },
            painter = painterResource(id = R.drawable.icon_won),
            contentDescription = "ExpenseAdditionWonImage",
            contentScale = ContentScale.Crop
        )

        TextField(
            modifier = Modifier
                .constrainAs(amountTextField) {
                    top.linkTo(parent.top)
                    start.linkTo(cashImage.end)
                    end.linkTo(parent.end)
                },
            value = amount,
            onValueChange = { amount = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Button(
            modifier = Modifier.constrainAs(incomeButton) {
                top.linkTo(amountTextField.bottom)
                start.linkTo(parent.start)
                end.linkTo(expenseButton.start)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            onClick = {
                moveToSecond(
                    navController = navController,
                    expenseType = ExpenseType.INCOME,
                    amount = amount
                )
            }
        ) {
            Text(text = "수입")
        }

        Button(
            modifier = Modifier.constrainAs(expenseButton) {
                top.linkTo(amountTextField.bottom)
                start.linkTo(incomeButton.end)
                end.linkTo(parent.end)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            onClick = {
                val expenseType = false
                moveToSecond(
                    navController = navController,
                    expenseType = ExpenseType.EXPENSE,
                    amount = amount
                )
            },

        ) {
            Text("지출")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController, expenseType: String, amount: String) {
    Log.d("@@ SecondScreen", "expenseType: $expenseType     amount: $amount")
}

private fun moveToSecond(navController: NavController, expenseType: ExpenseType, amount: String) {
    if (amount.isEmpty()) {
        // TODO : 예외 처리
        return
    }
    navController.navigate("second/${expenseType.name}/$amount")
}