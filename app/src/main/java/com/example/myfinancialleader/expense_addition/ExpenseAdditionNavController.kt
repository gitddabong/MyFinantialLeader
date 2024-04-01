package com.example.myfinancialleader.expense_addition

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinancialleader.R

@Composable
fun ExpenseAdditionNavController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "first"
    ) {
        composable("first") {
//            FirstScreen(navController)
        }
        composable("second") {
//            SecondScreen(navController)
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
fun ExpenseAdditionLayout(): ConstraintLayoutScope {




}

@Preview
@Composable
fun FirstScreen() {
    ConstraintLayout {
        val (cashImage, amountTextField) = createRefs()
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
            contentDescription = "ExpenseAdditionCashImage",
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
            onValueChange = { amount = it }
        )
    }
}