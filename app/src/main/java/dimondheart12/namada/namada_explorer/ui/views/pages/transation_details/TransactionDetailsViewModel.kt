package dimondheart12.namada.namada_explorer.ui.views.pages.transation_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val rpcNetwork: RPCNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<TransactionDetailsParameter.TransactionBlockParameter>>(
        DataState.Loading()
    )
        private set

    fun loadData(hash: String) {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val transaction = rpcNetwork.getTransaction(hash = hash)
                val block = rpcNetwork.getBlock(transaction.blockID)
                DataState.Success(
                    TransactionDetailsParameter.TransactionBlockParameter(
                        transaction = transaction,
                        block = block
                    )
                )
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}