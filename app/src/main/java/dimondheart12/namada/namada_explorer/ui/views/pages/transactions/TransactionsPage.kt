package dimondheart12.namada.namada_explorer.ui.views.pages.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.ItemView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.MiddleText
import dimondheart12.namada.namada_explorer.ui.views.pages.transation_details.TransactionDetailsParameter
import dimondheart12.namada.namada_explorer.util.timeAgoString
import java.util.Date

@Composable
fun TransactionsPage(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val pager = viewModel.data.collectAsLazyPagingItems()
    val now = Date()

    Surface {
        DataStateView(pager) { item ->
            ItemView(
                onClick = {
                    val block = viewModel.blocks[item.blockID]
                    if (block != null) {
                        navBackStackEntry?.savedStateHandle?.set(
                            "transaction",
                            TransactionDetailsParameter.TransactionBlockParameter(
                                item,
                                block
                            )
                        )
                        navController.navigate("transaction_details")
                    }
                }
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = item.txType.value, fontWeight = FontWeight.Bold)

                        Text(text = "•")

                        val block = viewModel.blocks[item.blockID]
                        if (block != null) {
                            Text(
                                text = block.header.time.timeAgoString(now = now),
                                modifier = Modifier,
                            )
                        }
                    }

                    MiddleText(text = item.hash.uppercase())
                }
            }
        }
    }
}