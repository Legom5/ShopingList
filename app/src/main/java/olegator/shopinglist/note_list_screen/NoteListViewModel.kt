package olegator.shopinglist.note_list_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import olegator.shopinglist.R
import olegator.shopinglist.add_item_screen.StringResourcesProvider
import olegator.shopinglist.data.NoteItem
import olegator.shopinglist.data.NoteRepository
import olegator.shopinglist.datastore.DataStoreManager
import olegator.shopinglist.dialog.DialogController
import olegator.shopinglist.dialog.DialogEvent
import olegator.shopinglist.shoping_list_screen.ShopingListEvent
import olegator.shopinglist.utils.UiEvent
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val stringProvider: StringResourcesProvider,
    private val repository: NoteRepository,
    dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    val noteListFlow = repository.getAllItems()

    private var noteItem: NoteItem? = null

    var noteList by mutableStateOf(listOf<NoteItem>())
    var originNoteList = listOf<NoteItem>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var titleColor = mutableStateOf("#FF03A9F4")

    var searchText by mutableStateOf("")
        private set

    override var dialogTitle = mutableStateOf(R.string.note_delete)
        private set

    override var editableText = mutableStateOf("")
        private set

    override var openDialog = mutableStateOf(false)
        private set

    override var showEditableText = mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                DataStoreManager.TITLE_COLOR,
                "#FF03A9F4"
            ).collect { color ->
                titleColor.value = color

            }
        }
        viewModelScope.launch {
            noteListFlow.collect { list ->
                noteList = list
                originNoteList = list

            }
        }
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.OnTextSearchChange -> {
                searchText = event.text
                noteList = originNoteList.filter { note ->
                    note.title.lowercase().startsWith(searchText.lowercase())
                }
            }

            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }

            is NoteListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is NoteListEvent.UnDoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }

            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    repository.deleteItem(noteItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar(stringProvider.getString(R.string.undone_snack_bar)))
                }
                openDialog.value = false
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}