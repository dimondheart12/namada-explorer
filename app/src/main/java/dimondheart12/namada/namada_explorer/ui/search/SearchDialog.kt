package dimondheart12.namada.namada_explorer.ui.search

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SearchDialog(onChangeText: (text: String) -> Unit) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = searchText,
        placeholder = { Text(text = "Input block height/transaction hash") },
        onValueChange = { newValue ->
            searchText = newValue
            onChangeText(newValue.text)
        }
    )
}