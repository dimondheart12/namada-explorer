package dimondheart12.namada.namada_explorer.ui.views.pages.proposals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.ItRedNamadaNetwork
import dimondheart12.namada.namada_explorer.domain.Proposals
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProposalsViewModel @Inject constructor(
    private val itRedNamadaNetwork: ItRedNamadaNetwork
) : ViewModel() {
    var dataState by mutableStateOf<DataState<List<Proposals.Proposal>>>(DataState.Loading())
        private set

    init {
        loadProposals()
    }

    private fun loadProposals() {
        dataState = DataState.Loading()
        viewModelScope.launch {
            dataState = try {
                val response = itRedNamadaNetwork.getProposals()
                DataState.Success(
                    response.proposals.sortedBy { proposal ->
                        proposal.id
                    }
                )
            } catch (e: Exception) {
                DataState.Throwable(e)
            }
        }
    }
}