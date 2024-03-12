package dimondheart12.namada.namada_explorer.ui.views.pages.block_details

import android.os.Parcelable
import dimondheart12.namada.namada_explorer.domain.Block
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class BlockDetailParameter : Parcelable {
    @Parcelize
    data class IdParameter(val id: String) : BlockDetailParameter(), Parcelable

    @Parcelize
    data class BlockParameter(
        val block: Block
    ) : BlockDetailParameter(), Parcelable
}