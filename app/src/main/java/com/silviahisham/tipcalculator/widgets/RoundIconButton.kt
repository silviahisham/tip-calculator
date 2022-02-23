package com.silviahisham.tipcalculator.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silviahisham.tipcalculator.R
import com.silviahisham.tipcalculator.ui.theme.TipCalculatorTheme

@Composable
fun RoundIconButton(icon: Painter, tint: Color = Color.Black, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(40.dp)
            .clickable { onClick.invoke() },
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Icon(painter = icon, contentDescription = "Icon", tint = tint)
    }
}

@Preview
@Composable
fun RoundIconButtonPreview() {
    TipCalculatorTheme {
        RoundIconButton(icon = painterResource(id = R.drawable.ic_baseline_add_24)) {}
    }
}