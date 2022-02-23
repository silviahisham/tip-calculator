package com.silviahisham.tipcalculator.utils

fun calculateTip(bill: Float, tipPercentage: Int) = (bill * tipPercentage) / 100

fun calculateTotalPerPerson(bill: Float, tip: Float, splitBy: Int) = (bill + tip) / splitBy
