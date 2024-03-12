@file:OptIn(ExperimentalLayoutApi::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dimondheart12.namada.namada_explorer.R
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardIcon
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateView
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun HomePage(viewModel: HomeViewModel = hiltViewModel()) {
    Surface {
        DataStateView(
            state = viewModel.dataState
        ) { data ->
            BoxWithConstraints(modifier = Modifier) {
                val boxWithConstraintsScope = this
                Box(modifier = Modifier) {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        maxItemsInEachRow = 2,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        CardVerticalView(
                            icon = CardIcon(
                                id = R.drawable.chip,
                                backgroundColor = Color.Green,
                                size = 32.dp,
                                padding = 8.dp
                            ),
                            title = data.network,
                            subTitle = "Network",
                            modifier = Modifier
                                .width((boxWithConstraintsScope.maxWidth / 2f) - 16.dp - 4.dp),
                        )

                        CardVerticalView(
                            icon = CardIcon(
                                id = R.drawable.block,
                                backgroundColor = Color.Cyan,
                                size = 32.dp,
                                padding = 8.dp
                            ),
                            title = data.blocksSize.formatWithCommas,
                            subTitle = "Block height",
                            modifier = Modifier
                                .width((boxWithConstraintsScope.maxWidth / 2f) - 16.dp - 4.dp),
                        )

                        CardVerticalView(
                            icon = CardIcon(
                                id = R.drawable.clock,
                                backgroundColor = Color.Yellow,
                                size = 32.dp,
                                padding = 8.dp
                            ),
                            title = data.latestBlockDate,
                            subTitle = "Latest Block Time",
                            modifier = Modifier
                                .width((boxWithConstraintsScope.maxWidth / 2f) - 16.dp - 4.dp),
                        )

                        CardVerticalView(
                            icon = CardIcon(
                                id = R.drawable.users,
                                backgroundColor = Color.DarkGray,
                                size = 32.dp,
                                padding = 8.dp
                            ),
                            title = data.validatorsSize.formatWithCommas,
                            subTitle = "Validators",
                            modifier = Modifier
                                .width((boxWithConstraintsScope.maxWidth / 2f) - 16.dp - 4.dp),
                        )
                    }
                }
            }
        }
    }
}