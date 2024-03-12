@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.transation_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.domain.Transaction
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateView
import dimondheart12.namada.namada_explorer.util.formatWithCommas
import dimondheart12.namada.namada_explorer.util.timeAgoString

@Composable
fun TransactionDetailsPage(
    navController: NavController,
    parameter: TransactionDetailsParameter,
    viewModel: TransactionDetailsViewModel = hiltViewModel()
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
                            text = "Information",
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
                when (parameter) {
                    is TransactionDetailsParameter.TransactionBlockParameter -> TransactionDetailsContentView(
                        transaction = parameter.transaction,
                        block = parameter.block
                    )

                    is TransactionDetailsParameter.TransactionHash -> {
                        val lifecycleOwner = LocalLifecycleOwner.current
                        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
                        if (lifecycleState == Lifecycle.State.STARTED) {
                            viewModel.loadData(hash = parameter.hash)
                        }

                        DataStateView(
                            viewModel.dataState
                        ) { transactionBlock ->
                            TransactionDetailsContentView(
                                transaction = transactionBlock.transaction,
                                block = transactionBlock.block
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionDetailsContentView(
    transaction: Transaction,
    block: Block
) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            CardVerticalView(
                title = transaction.hash.uppercase(),
                subTitle = "TxHash",
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardVerticalView(
                    title = block.header.chainID,
                    subTitle = "Chain ID",
                    modifier = Modifier
                        .weight(1f),
                )

                CardVerticalView(
                    title = block.header.height.toLong().formatWithCommas,
                    subTitle = "Height",
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardVerticalView(
                    title = block.header.time.timeAgoString(),
                    subTitle = "Time",
                    modifier = Modifier
                        .weight(1f),
                )

                CardVerticalView(
                    title = if (transaction.txType == Transaction.TxType.Wrapper) {
                        "Success"
                    } else if (transaction.returnCode == 0L) {
                        "Success"
                    } else {
                        "Failed"
                    },
                    subTitle = "Status",
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardVerticalView(
                    title = "${transaction.feeAmountPerGasUnit ?: "0"} NAAN",
                    subTitle = "Fee",
                    modifier = Modifier
                        .weight(1f),
                )

                CardVerticalView(
                    title = if (transaction.tx?.transfer?.shielded != null) {
                        "Yes"
                    } else {
                        "No"
                    },
                    subTitle = "Shielded",
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }
    }
}