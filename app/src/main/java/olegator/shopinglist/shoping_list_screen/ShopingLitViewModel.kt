package olegator.shopinglist.shoping_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import olegator.shopinglist.R
import olegator.shopinglist.data.ShopingListItem
import olegator.shopinglist.data.ShopingListRepository
import olegator.shopinglist.dialog.DialogEvent
import olegator.shopinglist.dialog.DialogController
import olegator.shopinglist.utils.UiEvent
import olegator.shopinglist.utils.getCurrentTime
import javax.inject.Inject

@HiltViewModel
class ShopingLitViewModel @Inject constructor(
    private val repository: ShopingListRepository
): ViewModel(), DialogController {

    val list = repository.getAllItems()

    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var listItem: ShopingListItem? = null

    override var dialogTitle = mutableStateOf(0)
        private set

    override var editableText= mutableStateOf(" ")
        private set

    override var openDialog= mutableStateOf(false)
        private set

    override var showEditableText= mutableStateOf(false)
        private set

    fun onEvent(event: ShopingListEvent){
        when(event){
            is ShopingListEvent.OnItemSave ->{
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShopingListItem(
                            listItem?.id,
                            editableText.value,
                            listItem?.time ?: getCurrentTime(),
                            listItem?.allItemsCount ?: 0,
                            listItem?.allSelectedItemsCount ?: 0
                        )
                    )
                }
            }
            is ShopingListEvent.OnItemClick ->{
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is ShopingListEvent.OnShowEditDialog ->{
                listItem = event.item
                openDialog.value = true
                editableText.value = listItem?.name ?: ""
                dialogTitle.value = R.string.edit_dialog
                showEditableText.value = true
            }
            is ShopingListEvent.OnShowDeleteDialog ->{
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = R.string.delete_dialog
                showEditableText.value = false
            }
        }
    }
override fun onDialogEvent(event: DialogEvent){
    when(event){
        is DialogEvent.OnCancel -> {
            openDialog.value = false
        }
        is DialogEvent.OnConfirm -> {
            if(showEditableText.value){
                onEvent(ShopingListEvent.OnItemSave)
            } else {
                viewModelScope.launch {
                    listItem?.let { repository.deleteItem(it) }
                }
            }
            openDialog.value = false
        }
        is DialogEvent.OnTextChange -> {
            editableText.value = event.text
        }
    }
}

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}