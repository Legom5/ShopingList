package olegator.shopinglist.note_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import olegator.shopinglist.R
import olegator.shopinglist.add_item_screen.StringResourcesProvider
import olegator.shopinglist.dialog.MainDialog
import olegator.shopinglist.shoping_list_screen.UiShopingListItem
import olegator.shopinglist.ui.theme.BlueLight
import olegator.shopinglist.ui.theme.EmptyText
import olegator.shopinglist.ui.theme.GreyLight
import olegator.shopinglist.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collect() { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEvent.route)
                }

                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(

                        message = uiEvent.message,
                        actionLabel = context.resources.getString(R.string.undone)
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.UnDoneDeleteItem)
                    }
                }

                else -> {}
            }

        }
    }
    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight,
                modifier = Modifier.padding(bottom = 100.dp),
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
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                TextField(
                    value = viewModel.searchText,
                    onValueChange = {text->
                        viewModel.onEvent(NoteListEvent.OnTextSearchChange(text))
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.search)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = BlueLight
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(viewModel.noteList) { item ->
                    UiNoteItem(viewModel.titleColor.value, item) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
            MainDialog(viewModel)
            if (viewModel.noteList.isEmpty()) {
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

    }

}