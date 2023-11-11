package com.sadig.spendtracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sadig.spendtracker.ui.viewmodel.HomeViewModel

@Composable
fun HistoryScreen(viewModel: HomeViewModel) {
    val currency = viewModel.getCurrency().collectAsState(initial = "Eur")
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        ShowSpendings(viewModel = viewModel, currency = currency.value ?: "", HomeViewModel.FetchType.MonthByMonth)
    }
}