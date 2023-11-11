package com.sadig.spendtracker.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.data.source.local.UserPreferencesDataSourceImpl
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import com.sadig.spendtracker.domain.repository.SpendingRepository
import com.sadig.spendtracker.domain.source.local.UserPreferencesDataSource
import com.sadig.spendtracker.domain.usecase.GetSpendingsInteractor
import com.sadig.spendtracker.domain.usecase.PutCurrencyInteractor
import com.sadig.spendtracker.domain.usecase.PutSpendingInteractor
import com.sadig.spendtracker.domain.usecase.ReadCurrencyInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val putCurrencyInteractor: PutCurrencyInteractor,
    private val getCurrencyInteractor: ReadCurrencyInteractor,
    private val putSpendingInteractor: PutSpendingInteractor,
    private val getSpendingsInteractor: GetSpendingsInteractor
) : ViewModel() {
    private val _shouldShowAddDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shouldShowAddDialog: StateFlow<Boolean> = _shouldShowAddDialog

    private val _spendingsOfThisMonth: MutableStateFlow<List<Spending>> = MutableStateFlow(listOf())
    val spendingOfThisMonth: StateFlow<List<Spending>> = _spendingsOfThisMonth

    private val _monthByMonthSpending: MutableStateFlow<List<Spending>> = MutableStateFlow(listOf())
    val monthByMonthSpending: StateFlow<List<Spending>> = _monthByMonthSpending
    enum class FetchType {
        ThisMonth,
        MonthByMonth
    }

    init {
        viewModelScope.launch {
            _spendingsOfThisMonth.emit(getSpendingsInteractor(FetchType.ThisMonth))
            _monthByMonthSpending.emit(getSpendingsInteractor(FetchType.MonthByMonth))
        }
    }

    fun setShowDialogStatus(status: Boolean) = viewModelScope.launch {
        _shouldShowAddDialog.emit(status)
    }

    sealed class EventType {
        object OnAddSpendButtonClicked : EventType()
    }

    fun saveSpending(title: String, description: String, amount: Double, date: Date) =
        viewModelScope.launch {
            _shouldShowAddDialog.emit(false)
            val spending = Spending(
                title = title,
                description = description,
                amount = amount,
                date = date
            )
            putSpendingInteractor(spending)
            _spendingsOfThisMonth.emit(
                _spendingsOfThisMonth.value.plus(spending).sortedByDescending { it.date })

            _monthByMonthSpending.emit(getSpendingsInteractor(FetchType.MonthByMonth))


        }

    fun onEvent(event: EventType) = viewModelScope.launch {
        when (event) {
            EventType.OnAddSpendButtonClicked -> {
                if (!_shouldShowAddDialog.value)
                    _shouldShowAddDialog.emit(true)
            }
        }
    }

    fun getCurrency(): Flow<String?> = getCurrencyInteractor()
    fun setCurrency(currency: String) = viewModelScope.launch {
        putCurrencyInteractor(currency)
    }
}