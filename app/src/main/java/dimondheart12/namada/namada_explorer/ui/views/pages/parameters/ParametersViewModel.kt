package dimondheart12.namada.namada_explorer.ui.views.pages.parameters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.TomlService
import dimondheart12.namada.namada_explorer.domain.NamadaExplorerParameters
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParametersViewModel @Inject constructor(
    private val tomlService: TomlService
) : ViewModel() {
    var dataState by mutableStateOf<DataState<NamadaExplorerParameters>>(DataState.Loading())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val response = tomlService.readParametersTomlFile()
                DataState.Success(response)
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}