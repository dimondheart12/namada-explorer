package dimondheart12.namada.namada_explorer.ui.views.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.data.StakePoolNetwork
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import dimondheart12.namada.namada_explorer.util.formatDate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rpcNetwork: RPCNetwork,
    private val stakePoolNetwork: StakePoolNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<HomeDatable>>(DataState.Loading())
        private set

    init {
        getData()
    }

    private fun getData() {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val blocks = rpcNetwork.getBlocks(1, 1)
                val validators = stakePoolNetwork.getValidators()
                val blockSize = blocks.total
                val latestBlock = blocks.data.first()
                val homeData = HomeDatable(
                    blocksSize = blockSize,
                    latestBlockDate = latestBlock.header.time.formatDate,
                    network = latestBlock.header.chainID,
                    validatorsSize = validators.currentValidatorsList.size
                )
                DataState.Success(homeData)
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}