package olegator.shopinglist.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<Int>
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>
    fun onDialogEvent(event: DialogEvent)
}