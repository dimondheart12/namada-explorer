package dimondheart12.namada.namada_explorer.ui.views.pages.transation_details

import android.os.Parcelable
import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.domain.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TransactionDetailsParameter : Parcelable {
    @Parcelize
    data class TransactionHash(
        val hash: String
    ) : TransactionDetailsParameter()

    @Parcelize
    data class TransactionBlockParameter(
        val transaction: Transaction,
        val block: Block
    ) : TransactionDetailsParameter()
}