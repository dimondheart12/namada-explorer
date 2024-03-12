package dimondheart12.namada.namada_explorer.ui.views.pages.validators

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.StakePoolNetwork
import dimondheart12.namada.namada_explorer.domain.Validators
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidatorsViewModel @Inject constructor(
    private val stakePoolNetwork: StakePoolNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<List<Validators.Validator>>>(
        DataState.Loading()
    )

    init {
        loadData()
    }

    private fun loadData() {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val response = stakePoolNetwork.getValidators()
                DataState.Success(response.currentValidatorsList)
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}