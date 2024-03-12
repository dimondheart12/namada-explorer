package dimondheart12.namada.namada_explorer.ui.views.pages.main

import androidx.annotation.DrawableRes
import dimondheart12.namada.namada_explorer.R

enum class MainPageState(val title: String, @DrawableRes val icon: Int) {
    HOME(title = "Home", icon = R.drawable.home),
    BLOCKS(title = "Block", icon = R.drawable.block),
    TRANSACTIONS(title = "Transaction", icon = R.drawable.network),
    VALIDATORS(title = "Validator", icon = R.drawable.shield),
    PROPOSALS(title = "Proposal", icon = R.drawable.star);

    val route: String
        get() {
            return name
        }
}