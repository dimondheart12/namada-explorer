@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.proposal_details

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dimondheart12.namada.namada_explorer.domain.Proposals
import dimondheart12.namada.namada_explorer.ui.views.custom_views.BarChartConfig
import dimondheart12.namada.namada_explorer.ui.views.custom_views.BarChartView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardView
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun ProposalDetailsPage(
    navController: NavController,
    proposal: Proposals.Proposal
) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    ),
                    title = {
                        Text(
                            text = "#${proposal.id}",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.padding(
                        PaddingValues(
                            horizontal = 16.dp,
                            vertical = 32.dp
                        )
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CardView(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                CardView(
                                    containerColor = Color.DarkGray,
                                    radius = 4.dp,
                                    paddingValues = PaddingValues(
                                        horizontal = 4.dp,
                                        vertical = 2.dp
                                    )
                                ) {
                                    Text(
                                        text = proposal.kind,
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }

                                Text(
                                    text = "Type" + "\n".repeat(
                                        2
                                    ),
                                    maxLines = 2,
                                    color = Color.Gray,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        CardView(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                CardView(
                                    containerColor = proposal.resultValue?.backgroundColor
                                        ?: Color.White,
                                    radius = 4.dp,
                                    paddingValues = PaddingValues(
                                        horizontal = 4.dp,
                                        vertical = 2.dp
                                    )
                                ) {
                                    Text(
                                        text = proposal.resultValue?.value ?: proposal.result,
                                        color = proposal.resultValue?.textColor ?: Color.Black,
                                        fontSize = 10.sp
                                    )
                                }

                                Text(
                                    text = "Status" + "\n".repeat(
                                        2
                                    ),
                                    maxLines = 2,
                                    color = Color.Gray,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    CardVerticalView(
                        title = proposal.author.account,
                        subTitle = "Author",
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CardVerticalView(
                            title = "${proposal.startEpoch.formatWithCommas}/${proposal.endEpoch.formatWithCommas}",
                            subTitle = "Epoch (Start/End)",
                            modifier = Modifier
                                .weight(1f),
                        )

                        CardVerticalView(
                            title = proposal.graceEpoch.formatWithCommas,
                            subTitle = "Grade epoch",
                            modifier = Modifier
                                .weight(1f),
                        )
                    }

                    CardView(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Votes",
                                maxLines = 1,
                                color = Color.Gray,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            if (proposal.resultValue == Proposals.Proposal.Result.Pending) {
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .height(24.dp)
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color.Black
                                        )
                                ) {
                                    Text(
                                        text = "Pending...",
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .background(Color.White)
                                            .fillMaxSize()
                                    )
                                }
                            } else {
                                BarChartView(
                                    fontSize = 16.sp,
                                    modifier = Modifier,
                                    values = listOf(
                                        BarChartConfig(
                                            title = "${proposal.yayVotes.toLong().formatWithCommas} Votes",
                                            value = proposal.yayVotes.toLong(),
                                            backgroundColor = Color.Green,
                                            textColor = Color.Black,
                                        ),
                                        BarChartConfig(
                                            title = "${proposal.nayVotes.toLong().formatWithCommas} Votes",
                                            value = proposal.nayVotes.toLong(),
                                            backgroundColor = Color.Red,
                                            textColor = Color.White,
                                        ),
                                        BarChartConfig(
                                            title = "${proposal.abstainVotes.toLong().formatWithCommas} Votes",
                                            value = proposal.abstainVotes.toLong(),
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