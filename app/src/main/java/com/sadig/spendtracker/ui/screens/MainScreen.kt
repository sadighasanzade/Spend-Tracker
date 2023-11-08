package com.sadig.spendtracker.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sadig.spendtracker.ui.theme.*
import com.sadig.spendtracker.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val currency = viewModel.getCurrency().collectAsState(initial = "Eur")
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.shouldShowAddDialog.collectAsState().value) {
            InputDialog(
                currency = currency.value ?: "",
                onDismiss = { viewModel.setShowDialogStatus(false) },
                onConfirm = { title, description, amount, date ->
                    viewModel.saveSpending(title, description, amount, date)
                })
        }
        SpinnerDialog(
            showDialog = currency.value.isNullOrBlank(),
            onDismiss = {},
            onOkClick = { currency ->
                viewModel.setCurrency(currency)
            }
        )

    }
    ShowSpendings()

}


@Composable
fun ShowSpendings() {

}


@Composable
fun SpinnerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onOkClick: (String) -> Unit
) {
    val options = listOf<String>("EUR", "AZN", "USD", "TL")
    var selectedValue by remember { mutableStateOf(options.first()) }
    var isDropdownVisible by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Select your currency",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Spinner
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Selected: ",
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .clickable { isDropdownVisible = true }
                                        .padding(16.dp)
                                        .padding(8.dp),
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = selectedValue,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .clickable { isDropdownVisible = true }
                                        .padding(16.dp)
                                        .padding(8.dp),
                                    style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                                )
                            }



                            if (isDropdownVisible) {
                                options.forEach { option ->
                                    OutlinedButton(
                                        onClick = {
                                            selectedValue = option
                                            isDropdownVisible = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                    ) {
                                        Text(text = option)
                                    }
                                }
                            }

                            // OK button
                            Button(
                                onClick = { onOkClick(selectedValue) },
                                shape = RoundedCornerShape(10.dp),

                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(text = "OK")
                            }
                        }
                }
            }
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun InputDialog(
    currency: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Double, Date) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var amount by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var showDateDialog by remember { mutableStateOf(false) }
    Surface( shape = RoundedCornerShape(16.dp)){
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Enter Details") },
            confirmButton = {
                Button(
                    onClick = {
                        val titleText = title.text
                        val amountText = amount.text.toDoubleOrNull()
                        if (amountText != null) {
                            onConfirm(titleText, description.text, amountText, selectedDate)
                            onDismiss()
                        }
                    },
                ) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() },
                ) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = { Text("Title") }
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = { Text("Description") }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = amount,
                            onValueChange = {
                                amount = it
                            },
                            label = { Text("Amount in $currency") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }

                    TextWithEndIcon(
                        icon = Icons.Filled.DateRange,
                        text = java.sql.Date(selectedDate.time).toString(),
                        modifier = Modifier.fillMaxWidth(),
                        description = "date"
                    ) {
                        showDateDialog = true
                    }
                    if (showDateDialog) {
                        SpendingDatePickerDialog(onDateSelected = {
                            selectedDate = it
                            showDateDialog = false
                        }) {
                            showDateDialog = false
                        }
                    }
                }
            }
        )
    }

}

@Composable
fun AddSpendingButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = { onClick() }, containerColor = LightRed) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Floating action button.",
            tint = Color.Black
        )
    }
}

@Composable
fun TextWithEndIcon(
    icon: ImageVector,
    text: String,
    modifier: Modifier,
    description: String,
    onIconClicked: () -> Unit
) {
    Row(modifier = modifier) {
        Text(text = text, fontSize = 16.sp)
        Spacer(Modifier.size(20.dp))
        Icon(imageVector = icon, contentDescription = description, Modifier.clickable {
            onIconClicked()
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingDatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(Instant.now().toEpochMilli())

    val selectedDate = datePickerState.selectedDateMillis?.let {
        Date(it)
    } ?: Date(Instant.now().toEpochMilli())

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}