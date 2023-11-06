package com.sadig.spendtracker.ui.models

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sadig.spendtracker.R

sealed class BottomNavigationItem(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit
) {
    object Home : BottomNavigationItem("home", "Home",
        {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "home",
                modifier =
                Modifier.size(28.dp)
            )
        })

    object History : BottomNavigationItem("history", "History", {
        Icon(
            painter = painterResource(id = R.drawable.history),
            contentDescription = "history",
            modifier =
            Modifier.size(28.dp)
        )
    })

    object Graphs : BottomNavigationItem("graphs", "Graphs", {
        Icon(
            painter = painterResource(id = R.drawable.graph),
            contentDescription = "graphs",
            modifier =
            Modifier.size(28.dp)
        )
    })
}