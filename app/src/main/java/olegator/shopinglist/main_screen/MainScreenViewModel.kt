package olegator.shopinglist.main_screen


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
import olegator.shopinglist.dialog.DialogController
import olegator.shopinglist.dialog.DialogEvent
import olegator.shopinglist.utils.Routes
import olegator.shopinglist.utils.UiEvent
import olegator.shopinglist.utils.getCurrentTime
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShopingListRepository
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf(R.string.edit_dialog)
        private set

    override var editableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set

    override var showEditableText = mutableStateOf(true)
        private set

    var showFloatingButton = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShopingListItem(
                            null,
                            editableText.value,
                            getCurrentTime(),
                            0,
                            0
                        )
                    )
                }
            }

            is MainScreenEvent.OnNewItemClick -> {
                if(event.route == Routes.SHOPING_LIST){
                    openDialog.value = true
                } else {
                    sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
                }

            }

            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                showFloatingButton.value =
                    if (event.route == Routes.ABOUT || event.route == Routes.SETTINGS) {
                        false
                    } else {
                        true
                    }
            }

            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }

            is DialogEvent.OnConfirm -> {
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editableText.value = ""
            }

            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}