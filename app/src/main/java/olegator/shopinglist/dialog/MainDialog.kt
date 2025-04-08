package olegator.shopinglist.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import olegator.shopinglist.R
import olegator.shopinglist.ui.theme.DarkText
import olegator.shopinglist.ui.theme.GreyLight

@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
                title = null,
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = dialogController.dialogTitle.value),
                            style = TextStyle(
                                color = DarkText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (dialogController.showEditableText.value) TextField(
                        value = dialogController.editableText.value,
                        onValueChange = {text ->
                            dialogController.onDialogEvent(DialogEvent.OnTextChange(text))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.dialog_list_field))
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = GreyLight,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(9.dp),
                        singleLine = true,
                        textStyle = TextStyle(
                            color = DarkText,
                            fontSize = 16.sp
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnConfirm)
                }) {
                    Text(text = stringResource(id = R.string.confirm_button))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogController.onDialogEvent(DialogEvent.OnCancel)
                }) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
            }
        )
    }
}