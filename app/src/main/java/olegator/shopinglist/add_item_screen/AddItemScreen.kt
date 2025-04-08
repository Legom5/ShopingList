package olegator.shopinglist.add_item_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import olegator.shopinglist.R
import olegator.shopinglist.banner.Banner
import olegator.shopinglist.dialog.MainDialog
import olegator.shopinglist.ui.theme.BlueLight
import olegator.shopinglist.ui.theme.DarkText
import olegator.shopinglist.ui.theme.EmptyText
import olegator.shopinglist.ui.theme.GreyLight
import olegator.shopinglist.utils.UiEvent
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{uiEvent ->
            when(uiEvent){
                is UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }

        }
    }
    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight
            )

        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = { text ->
                            viewModel.onEvent(AddItemEvent.OnTextChange(text))
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.new_add_item),
                                fontSize = 12.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = BlueLight,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText
                        ),
                        singleLine = true
                    )
                    IconButton(onClick = {
                        viewModel.onEvent(AddItemEvent.OnItemSave)

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add"
                        )
                    }
                }
            }
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 5.dp,
                        end = 5.dp
                    ),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                if (itemsList != null) {
                    items(itemsList.value) { item ->
                        UiAddItem(item = item, onEvent = { event ->
                            viewModel.onEvent(event)
                        })
                    }
                }

            }


        }
        MainDialog(viewModel)
        if(itemsList?.value?.isEmpty() == true){
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
    Spacer(modifier = Modifier.height(100.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Banner(id = R.string.banner_add_item)

    }

}
    



