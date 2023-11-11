package com.sadig.spendtracker.ui.screens

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.ui.viewmodel.HomeViewModel
import kotlin.math.round

@Composable
fun GraphsScreen(viewModel: HomeViewModel) {
    val gradientColors =
        listOf(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.secondary)
    val items = viewModel.spendingOfThisMonth.collectAsState()
    val currency = viewModel.getCurrency().collectAsState(initial = "")
    if(items.value.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "Spend graph of this month", fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            LineChartOfSpending(items.value)
            Spacer(modifier = Modifier.size(20.dp))
            DetailsOfSpendings(items.value, currency = currency.value ?: "")
        }
    }
}

@Composable
fun DetailsOfSpendings(items: List<Spending>, currency: String) {
    val maxSpending = items.maxBy { it.amount }.amount
    val sum = items.sumOf { it.amount }
    val count = items.size
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append("Max spending:")
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(" ${maxSpending} ${currency}")
                    }
                },
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(12.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append("Total spending:")
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(" ${sum} ${currency}")
                    }
                },
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(12.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append("Count of spending:")
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(" ${count} ${currency}")
                    }
                },
                fontSize = 18.sp
            )
        }
    }

}

@Composable
fun LineChartOfSpending(items: List<Spending>) {
    val points = items.map {
        val day = DateFormat.format("dd", it.date).toString()
        Point(x = day.toFloat(), y = it.amount.toFloat())
    }
    val steps = 5
    val maxAmount = points.maxBy { it.y }.y
    val maxDay = points.maxBy { it.x }.x
    val minDay = points.minBy { it.x }.x
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Transparent)
        .steps(maxDay.toInt())
        .labelData { i -> (i + minDay).toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLabelColor(color = MaterialTheme.colorScheme.tertiary)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .axisLabelColor(color = MaterialTheme.colorScheme.tertiary)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .labelData { i ->
            val yScale = maxAmount / (steps)
            round(i * yScale * 10 / 10.0).toString()
        }.build()


    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = points,
                    LineStyle(color = MaterialTheme.colorScheme.tertiary),
                    IntersectionPoint(color = MaterialTheme.colorScheme.tertiary),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.tertiary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = null,
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}