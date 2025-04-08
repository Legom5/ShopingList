package olegator.shopinglist.add_item_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import olegator.shopinglist.R
import olegator.shopinglist.data.AddItem
import olegator.shopinglist.data.AddItemRepository
import olegator.shopinglist.data.ShopingListItem
import olegator.shopinglist.dialog.DialogController
import olegator.shopinglist.dialog.DialogEvent
import olegator.shopinglist.main_screen.MainScreenEvent
import olegator.shopinglist.utils.UiEvent
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val stringProvider: StringResourcesProvider,
    private val repository: AddItemRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController {


    private val _uiEvent = Channel<UiEvent> ()
    val uiEvent = _uiEvent.receiveAsFlow()

   var itemsList: Flow<List<AddItem>>?  = null
    var addItem: AddItem? = null
    var shopingListItem: ShopingListItem? = null
    var listId: Int = -1
    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemsList = repository.getAllItemsById(listId)
        viewModelScope.launch {
            shopingListItem = repository.getListItemById(listId)
        }
    }

    var itemText = mutableStateOf("")
        private set

    override var dialogTitle = mutableStateOf(R.string.edit_add_item)
        private set

    override var editableText= mutableStateOf("")
        private set

    override var openDialog= mutableStateOf(false)
        private set

    override var showEditableText= mutableStateOf(true)
        private set

    fun onEvent(event: AddItemEvent){
        when(event){
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if(listId == -1) return@launch
                    if(addItem != null){
                        if(addItem!!.name.isEmpty()){
                            sendUiEvent(UiEvent.ShowSnackBar(stringProvider.getString(R.string.snak_bar)))
                            return@launch
                        }
                    } else {
                        if(itemText.value.isEmpty()){
                            sendUiEvent(UiEvent.ShowSnackBar(stringProvider.getString(R.string.snak_bar)))
                            return@launch
                        }
                    }
                    repository.insertItem(
                        AddItem(
                            addItem?.id,
                            addItem?.name ?: itemText.value,
                            addItem?.isCheck ?: false,
                            listId
                        )

                       )
                    itemText.value = ""
                    addItem = null
                }
                updateShopingListCount()
            }
            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editableText.value = addItem?.name ?: ""
            }
            is AddItemEvent.OnTextChange -> {
                itemText.value = event.text

            }
            is AddItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                updateShopingListCount()
            }
            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    repository.insertItem(event.item)
                }
                updateShopingListCount()
            }
        }

    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                addItem = addItem?.copy(name = editableText.value )
                editableText.value = ""
                onEvent(AddItemEvent.OnItemSave)
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private fun updateShopingListCount(){
        viewModelScope.launch {
            itemsList?.collect{list ->
                var counter = 0
                list.forEach{item ->
                    if (item.isCheck)  counter++
                }
                shopingListItem?.copy(
                    allItemsCount = list.size,
                    allSelectedItemsCount = counter
                )?.let {shItem ->
                    repository.insertItem(
                        shItem
                    )
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}