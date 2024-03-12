package dimondheart12.namada.namada_explorer.ui.views.pages.transactions

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.util.explorerPager
import dimondheart12.namada.namada_explorer.util.getValue
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val rpcNetwork: RPCNetwork
) : ViewModel() {
    val data by explorerPager { page ->
        val transactions = rpcNetwork.getTransactions(
            page = page,
            pageSize = 20
        )
        for (transaction in transactions.data) {
            val elementBlock = blocks[transaction.blockID]
            if (elementBlock == null) {
                blocks[transaction.blockID] = rpcNetwork.getBlock(id = transaction.blockID)
            }
        }
        return@explorerPager transactions
    }
    val blocks: MutableMap<String, Block> = mutableMapOf()
}