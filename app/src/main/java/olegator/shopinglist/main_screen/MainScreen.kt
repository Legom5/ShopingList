package olegator.shopinglist.main_screen

import android.annotation.SuppressLint
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collect
import olegator.shopinglist.R
import olegator.shopinglist.dialog.MainDialog
import olegator.shopinglist.navigation.NavigationGraph
import olegator.shopinglist.shoping_list_screen.ShopingLitViewModel
import olegator.shopinglist.utils.Routes
import olegator.shopinglist.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavHostController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.NavigateMain ->{
                    mainNavHostController.navigate(uiEvent.route)
                }
                is UiEvent.Navigate ->{
                    navController.navigate(uiEvent.route)
                }
                else -> {}
            }

        }
    }
    Scaffold(
        bottomBar = {
            BottomNav(currentRoute){route->
                viewModel.onEvent(MainScreenEvent.Navigate(route))

            }
        },
        floatingActionButton = {
           if(viewModel.showFloatingButton.value) FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MainScreenEvent.OnNewItemClick(currentRoute ?:Routes.SHOPING_LIST))
                }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.add_icon
                    ),
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        NavigationGraph(navController) { route ->
            viewModel.onEvent(MainScreenEvent.NavigateMain(route))
        }

        MainDialog(viewModel)

    }
}