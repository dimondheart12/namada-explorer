package dimondheart12.namada.namada_explorer.ui.views.pages.validators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateListView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.ItemView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.MiddleText
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun ValidatorsPage(
    navController: NavController,
    viewModel: ValidatorsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Surface {
        DataStateListView(viewModel.dataState) { item ->
            ItemView(
                onClick = {
                    navBackStackEntry?.savedStateHandle?.set(
                        "validator",
                        item
                    )
                    navController.navigate("validator_details")
                }
            ) {
                Column {
                    if (item.moniker.trim().isBlank()) {
                        MiddleText(
                            text = item.address.uppercase(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = item.moniker,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${item.votingPower.formatWithCommas} NAAN",
                            fontSize = 14.sp
                        )

                        Text(
                            text = "(${item.votingPercentage.formatWithCommas(4)}%)",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}