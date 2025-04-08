package olegator.shopinglist.new_note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect
import olegator.shopinglist.R
import olegator.shopinglist.banner.Banner
import olegator.shopinglist.ui.theme.BlueLight
import olegator.shopinglist.ui.theme.DarkText
import olegator.shopinglist.ui.theme.GreyLight
import olegator.shopinglist.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackBar -> {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = GreyLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.title,
                            onValueChange = { text ->
                                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                            },
                            label = {
                                Text(
                                    stringResource(id = R.string.note_title),
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = BlueLight
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        )
                        IconButton(
                            onClick = {
                                viewModel.onEvent(NewNoteEvent.OnSave)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.save_icon),
                                contentDescription = "Save",
                                tint = BlueLight
                            )
                        }
                    }
                    TextField(
                        modifier = Modifier.padding(bottom = 50.dp),
                        value = viewModel.description,
                        onValueChange = { text ->
                            viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.note_description),
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = DarkText
                        ),

                    )


                }
            }
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Banner(id = R.string.banner_new_note)
    }

}