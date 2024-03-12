package dimondheart12.namada.namada_explorer.ui.views.pages.validator_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.StakePoolNetwork
import dimondheart12.namada.namada_explorer.domain.BlockSignature
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidatorDetailsViewModel @Inject constructor(
    private val stakePoolNetwork: StakePoolNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<List<BlockSignature>>>(DataState.Loading())
        private set

    fun loadData(address: String) {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val response = stakePoolNetwork.getBlockSignatures(address = address)
                DataState.Success(response)
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}