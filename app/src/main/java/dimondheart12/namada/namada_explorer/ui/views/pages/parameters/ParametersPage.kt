@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.parameters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateScrollView
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun ParametersPage(
    navController: NavController,
    viewModel: ParametersViewModel = hiltViewModel()
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
                            text = "Parameters",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.padding(4.dp),
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
                DataStateScrollView(state = viewModel.dataState) { parameters ->
                    parametersComposable(
                        title = "Chain Parameters",
                        parameterDisplays = listOf(
                            ParametersDisplay(
                                title = "Max Tx Bytes",
                                (parameters.parameters.maxTxBytes ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Native Token",
                                "NAAN"
                            ),
                            ParametersDisplay(
                                title = "Min Num Of Blocks",
                                (parameters.parameters.minNumOfBlocks ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Expected Time Per Block",
                                (parameters.parameters.maxExpectedTimePerBlock
                                    ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Proposal Bytes",
                                (parameters.parameters.maxProposalBytes ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Epochs Per Year",
                                (parameters.parameters.epochsPerYear ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Signatures Per Transaction",
                                (parameters.parameters.maxSignaturesPerTransaction
                                    ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Block Gas",
                                (parameters.parameters.maxBlockGas ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Fee Unshielding Gas Limit",
                                (parameters.parameters.feeUnshieldingGasLimit ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Minimum Gas Price",
                                parameters.minimumGasPrice.naan
                            ),
                        )
                    )
                    parametersComposable(
                        title = "Proof of Stake Parameters",
                        parameterDisplays = listOf(
                            ParametersDisplay(
                                title = "Max Validator Slots",
                                (parameters.posParams.maxValidatorSlots ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Pipeline Length",
                                (parameters.posParams.pipelineLen ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Unbonding Length",
                                (parameters.posParams.unbondingLen ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "TM Votes Per Token",
                                parameters.posParams.tmVotesPerToken ?: ""
                            ),
                            ParametersDisplay(
                                title = "Block Proposer Reward",
                                parameters.posParams.blockProposerReward ?: ""
                            ),
                            ParametersDisplay(
                                title = "Block Vote Reward",
                                parameters.posParams.blockVoteReward ?: ""
                            ),
                            ParametersDisplay(
                                title = "Max Inflation Rate",
                                parameters.posParams.maxInflationRate ?: ""
                            ),
                            ParametersDisplay(
                                title = "Target Staked Ratio",
                                parameters.posParams.targetStakedRatio ?: ""
                            ),
                            ParametersDisplay(
                                title = "Duplicate Vote Min Slash Rate",
                                parameters.posParams.duplicateVoteMinSlashRate ?: ""
                            ),
                            ParametersDisplay(
                                title = "Light Client Attack Min Slash Rate",
                                parameters.posParams.lightClientAttackMinSlashRate ?: ""
                            ),
                            ParametersDisplay(
                                title = "Cubic Slashing Window Length",
                                (parameters.posParams.cubicSlashingWindowLength
                                    ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Validator Stake Threshold",
                                parameters.posParams.validatorStakeThreshold ?: ""
                            ),
                            ParametersDisplay(
                                title = "Liveness Window Check",
                                (parameters.posParams.livenessWindowCheck ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Liveness Threshold",
                                parameters.posParams.livenessThreshold ?: ""
                            ),
                            ParametersDisplay(
                                title = "Rewards Gain P",
                                parameters.posParams.rewardsGainP ?: ""
                            ),
                            ParametersDisplay(
                                title = "Rewards Gain D",
                                parameters.posParams.rewardsGainD ?: ""
                            )
                        )
                    )
                    parametersComposable(
                        title = "Chain Parameters",
                        parameterDisplays = listOf(
                            ParametersDisplay(
                                title = "Min Proposal Fund",
                                (parameters.govParams.minProposalFund ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Proposal Code Size",
                                (parameters.govParams.maxProposalCodeSize ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Min Proposal Voting Period",
                                (parameters.govParams.minProposalVotingPeriod ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Proposal Period",
                                (parameters.parameters.minNumOfBlocks ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Max Proposal Content Size",
                                (parameters.govParams.maxProposalContentSize ?: 0).formatWithCommas
                            ),
                            ParametersDisplay(
                                title = "Min Proposal Grace Epochs",
                                (parameters.govParams.minProposalGraceEpochs ?: 0).formatWithCommas
                            )
                        )
                    )
                }
            }
        }
    }
}

data class ParametersDisplay(
    val title: String,
    val value: String
)

private fun LazyListScope.parametersComposable(
    title: String,
    parameterDisplays: List<ParametersDisplay>
) {
    item {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    item {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(parameterDisplays) { display ->
                CardVerticalView(
                    title = display.value,
                    subTitle = display.title,
                    modifier = Modifier.width(300.dp),
                )
            }
        }
    }
}