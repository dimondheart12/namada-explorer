package dimondheart12.namada.namada_explorer.ui.views.pages.block_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockDetailsViewModel @Inject constructor(
    private val rpcNetwork: RPCNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<Block>>(DataState.Loading())
        private set

    fun loadData(id: String) {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val response = rpcNetwork.getBlock(id = id)
                DataState.Success(response)
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}