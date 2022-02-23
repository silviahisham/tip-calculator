package com.silviahisham.tipcalculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silviahisham.tipcalculator.ui.theme.Purple100
import com.silviahisham.tipcalculator.ui.theme.TipCalculatorTheme
import com.silviahisham.tipcalculator.utils.calculateTip
import com.silviahisham.tipcalculator.utils.calculateTotalPerPerson
import com.silviahisham.tipcalculator.widgets.RoundIconButton

val space = 8.dp
val doubleSpace = 16.dp

@Composable
fun MainScreen() {
    val totalAmountState = remember { mutableStateOf(0F) }

    val billState = remember { mutableStateOf("") }
    val splitByState = remember { mutableStateOf(1) }
    val sliderState = remember { mutableStateOf(0F) }
    val tipAmount = remember { mutableStateOf(0F) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopHeader(totalAmountState)
            BillForm(billState, splitByState, sliderState, tipAmount, totalAmountState)
        }
    }
}

@Composable
fun TopHeader(total: MutableState<Float>) {
    val totalFormatted = "%.1f".format(total.value)
    Surface(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(space),
        shape = RoundedCornerShape(space),
        color = Purple100
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = stringResource(id = R.string.header_title), style = MaterialTheme.typography.h6)
            Text(
                text = "$$totalFormatted",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BillForm(
    billState: MutableState<String>,
    splitByState: MutableState<Int>,
    sliderState: MutableState<Float>,
    tipAmountState: MutableState<Float>,
    total: MutableState<Float>
) {
    val tipPercentage = (sliderState.value * 100).toInt()

    Surface(
        modifier = Modifier
            .padding(space)
            .fillMaxWidth(),
        shape = RoundedCornerShape(space),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(doubleSpace)
        ) {
            OutlinedTextField(
                value = billState.value,
                onValueChange = { billState.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Enter Bill") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_attach_money_24),
                        contentDescription = "Bill icon"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            // Split Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = doubleSpace),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Split")
                RoundIconButton(icon = painterResource(id = R.drawable.ic_baseline_remove_24), onClick = {
                    if (splitByState.value > 1) splitByState.value--
                    billState.value.toFloatOrNull()?.let { bill ->
                        total.value = calculateTotalPerPerson(bill, tipAmountState.value, splitByState.value)
                    }
                })
                Text(text = splitByState.value.toString())
                RoundIconButton(icon = painterResource(id = R.drawable.ic_baseline_add_24), onClick = {
                    splitByState.value++
                    billState.value.toFloatOrNull()?.let { bill ->
                        total.value = calculateTotalPerPerson(bill, tipAmountState.value, splitByState.value)
                    }
                })
            }
            // Tip Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = doubleSpace),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Tip")
                Text(text = "$${tipAmountState.value}")
            }
            // Slider
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = doubleSpace),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${tipPercentage}%")
                Slider(
                    value = sliderState.value,
                    onValueChange = {
                        sliderState.value = it

                        billState.value.toFloatOrNull()?.let { bill ->
                            tipAmountState.value = calculateTip(bill, tipPercentage)
                            total.value = calculateTotalPerPerson(bill, tipAmountState.value, splitByState.value)
                        }
                    },
                    steps = 10
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        MainScreen()
    }
}