package dimondheart12.namada.namada_explorer.ui.views.pages.proposals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dimondheart12.namada.namada_explorer.domain.Proposals
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateListView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.ItemView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.ProgressBarConfig
import dimondheart12.namada.namada_explorer.ui.views.custom_views.ProgressBarMultipleValueView
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun ProposalsPage(
    navController: NavController,
    viewModel: ProposalsViewModel = hiltViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Surface {
        DataStateListView(viewModel.dataState) { item ->
            ItemView(
                onClick = {
                    navBackStackEntry?.savedStateHandle?.set(
                        "proposal",
                        item
                    )
                    navController.navigate("proposal_details")
                }) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.id.toString(),
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )

                        Text(text = "•")

                        CardView(
                            containerColor = Color.DarkGray,
                            radius = 4.dp,
                            paddingValues = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.kind,
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }

                        CardView(
                            containerColor = item.resultValue?.backgroundColor ?: Color.White,
                            radius = 4.dp,
                            paddingValues = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.resultValue?.value ?: item.result,
                                color = item.resultValue?.textColor ?: Color.Black,
                                fontSize = 10.sp
                            )
                        }
                    }

                    var progressModifier: Modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .height(16.dp)

                    if (item.resultValue == Proposals.Proposal.Result.Pending) {
                        progressModifier = progressModifier then Modifier.border(
                            width = 1.dp,
                            shape = RoundedCornerShape(7.dp),
                            color = Color.Black
                        )
                    }

                    CardView(
                        containerColor = Color.Transparent,
                        radius = 8.dp,
                        paddingValues = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = progressModifier
                        ) {
                            if (item.resultValue == Proposals.Proposal.Result.Pending) {
                                Text(
                                    text = "Pending...",
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxSize()
                                )
                            } else {
                                ProgressBarMultipleValueView(
                                    values = listOf(
                                        ProgressBarConfig(
                                            title = "${item.yayVotes.toLong().formatWithCommas} Votes",
                                            value = item.yayVotes.toLong(),
                                            backgroundColor = Color.Green,
                                            textColor = Color.Black,
                                        ),
                                        ProgressBarConfig(
                                            title = "${item.nayVotes.toLong().formatWithCommas} Votes",
                                            value = item.nayVotes.toLong(),
                                            backgroundColor = Color.Red,
                                            textColor = Color.White,
                                        ),
                                        ProgressBarConfig(
                                            title = "${item.abstainVotes.toLong().formatWithCommas} Votes",
                                            value = item.abstainVotes.toLong(),
                                            backgroundColor = Color.Gray,
                                            textColor = Color.White,
                                        ),
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}