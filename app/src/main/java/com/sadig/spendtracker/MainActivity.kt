package com.sadig.spendtracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sadig.spendtracker.ui.models.BottomNavigationItem
import com.sadig.spendtracker.ui.screens.AddSpendingButton
import com.sadig.spendtracker.ui.screens.GraphsScreen
import com.sadig.spendtracker.ui.screens.HistoryScreen
import com.sadig.spendtracker.ui.screens.HomeScreen
import com.sadig.spendtracker.ui.theme.SpendTrackerTheme
import com.sadig.spendtracker.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityRetainedScoped

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel by viewModels<HomeViewModel>()
        setContent {
            SpendTrackerTheme {
                ScreenWithBottomAppBar(viewModel = viewmodel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenWithBottomAppBar(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = {
            AddSpendingButton {
                viewModel.onEvent(HomeViewModel.EventType.OnAddSpendButtonClicked)
            }
        }
    ) {
        Navigation(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Graphs,
        BottomNavigationItem.Home,
        BottomNavigationItem.History,
    )
    BottomNavigation(
        backgroundColor = Color.Red,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = item.icon,
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, viewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.route) {
        composable(BottomNavigationItem.Graphs.route) { GraphsScreen() }
        composable(BottomNavigationItem.Home.route) { HomeScreen(viewModel) }
        composable(BottomNavigationItem.History.route) { HistoryScreen() }
    }
}


