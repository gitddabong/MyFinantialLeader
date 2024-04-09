package com.example.myfinancialleader.expense_addition

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfinancialleader.R
import com.example.myfinancialleader.expense_addition.data.ExpenseAdditionCategoryData
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
fun ExpenseAdditionLayout(onBackPressed: (() -> Unit)? = null, content: @Composable ConstraintLayoutScope.() -> Unit) {
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
                onBackPressed?.invoke()
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
    ExpenseAdditionLayout(
        onBackPressed = { navController.popBackStack() }
    ) {
        val (detailTextField, categoryList) = createRefs()

        var detail by remember {
            mutableStateOf("")
        }

        TextField(
            modifier = Modifier
                .constrainAs(detailTextField) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            value = detail,
            onValueChange = { detail = it }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp), // 세로 간격을 0으로 설정합니다.
            horizontalArrangement = Arrangement.spacedBy(10.dp), // 가로 간격을 0으로 설정합니다.
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(categoryList) {
                    top.linkTo(detailTextField.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(20.dp, 0.dp)
        ) {
            itemsIndexed(getDummyData()) { index, item ->
                CellItem(
                    data = item,
                    onClickEvent = {
                        // TODO : 아이템 추가 로직
                    }
                )
            }
        }
    }
}

@Composable
fun CellItem(data: ExpenseAdditionCategoryData, onClickEvent: () -> Unit) {
    Button(onClick = {
        onClickEvent.invoke()
    }) {
        ConstraintLayout {
            val (categoryImage, categoryDetailText) = createRefs()

            Image(
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(categoryImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = data.imageResource),
                contentDescription = "ExpenseAdditionBackButton",
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(categoryDetailText) {
                        top.linkTo(categoryImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = data.categoryTitle,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun getDummyData(): List<ExpenseAdditionCategoryData> {
    return listOf(
        ExpenseAdditionCategoryData(R.drawable.category_sample_cash, "송금"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_car, "교통비"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_coffee, "카페"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_dinner, "식사"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_exercise, "스포츠"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_fast_food, "패스트 푸드"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_game, "여가"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_gift, "선물"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_health, "건강"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_meat, "식비"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_multi_player, "구독제 서비스"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_music, "취미"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_phone, "통신비"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_pokemon, "포켓몬 카드"),
        ExpenseAdditionCategoryData(R.drawable.category_sample_shopping, "쇼핑"),
    )
}


private fun moveToSecond(navController: NavController, expenseType: ExpenseType, amount: String) {
    if (amount.isEmpty()) {
        // TODO : 예외 처리
        return
    }
    navController.navigate("second/${expenseType.name}/$amount")
}