package olegator.shopinglist.add_item_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import olegator.shopinglist.R
import olegator.shopinglist.data.AddItem
import olegator.shopinglist.ui.theme.DarkText


@Composable
fun UiAddItem(
    item: AddItem,
    onEvent: (AddItemEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                onEvent(AddItemEvent.OnShowEditDialog(item))
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.isCheck)Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = item.name,
                style =
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                ,
                fontSize = 12.sp,
                color = DarkText
            )
            else Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = item.name,
                fontSize = 12.sp,
                color = DarkText
            )
            Checkbox(
                checked = item.isCheck,
                onCheckedChange = { isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(isCheck = isChecked)))
                }
            )
            IconButton(
                onClick = {
                onEvent(AddItemEvent.OnDelete(item))
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}