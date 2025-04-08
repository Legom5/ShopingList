package olegator.shopinglist.shoping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import olegator.shopinglist.R
import olegator.shopinglist.dialog.MainDialog
import olegator.shopinglist.ui.theme.EmptyText
import olegator.shopinglist.ui.theme.GreyLight
import olegator.shopinglist.utils.UiEvent

@Composable
fun ShopingListScreen(
    viewModel: ShopingLitViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val itemsList = viewModel.list.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect(){ uiEvent ->
            when(uiEvent){
                is UiEvent.Navigate ->{
                    onNavigate(uiEvent.route)
                }
                else -> {}
            }

        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyLight),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(itemsList.value){item ->
            UiShopingListItem(item){event ->
                viewModel.onEvent(event)

            }
        }
    }
    MainDialog(viewModel)
    if(itemsList.value.isEmpty()){
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = stringResource(id = R.string.empty),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = EmptyText
        )
    }
}