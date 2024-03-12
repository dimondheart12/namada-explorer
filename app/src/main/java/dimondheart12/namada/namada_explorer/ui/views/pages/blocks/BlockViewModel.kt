package dimondheart12.namada.namada_explorer.ui.views.pages.blocks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.util.explorerPager
import dimondheart12.namada.namada_explorer.util.getValue
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val rpcNetwork: RPCNetwork
) : ViewModel() {
    val data by explorerPager { page ->
        rpcNetwork.getBlocks(
            page = page,
            pageSize = 20
        )
    }
}