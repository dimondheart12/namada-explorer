@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.block_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.MiddleText
import dimondheart12.namada.namada_explorer.ui.views.pages.transation_details.TransactionDetailsParameter
import dimondheart12.namada.namada_explorer.util.formatWithCommas
import dimondheart12.namada.namada_explorer.util.timeAgoString
import java.util.Date

@Composable
fun BlockDetailsContentView(
    navController: NavController,
    parameter: BlockDetailParameter,
    viewModel: BlockDetailsViewModel = hiltViewModel()
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
                            text = "Header",
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
                    is BlockDetailParameter.BlockParameter -> BlockDetailsContentView(
                        navController = navController,
                        block = parameter.block
                    )

                    is BlockDetailParameter.IdParameter -> {
                        val lifecycleOwner = LocalLifecycleOwner.current
                        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
                        if (lifecycleState == Lifecycle.State.STARTED) {
                            viewModel.loadData(id = parameter.id)
                        }

                        DataStateView(
                            viewModel.dataState
                        ) { block ->
                            BlockDetailsContentView(
                                navController = navController,
                                block = block
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BlockDetailsContentView(
    navController: NavController,
    block: Block
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val now = Date()

    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            CardVerticalView(
                title = block.blockID.uppercase(),
                subTitle = "ID",
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            CardVerticalView(
                title = block.header.proposerAddress.uppercase(),
                subTitle = "Proposer address",
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
                    title = block.header.time.timeAgoString(now = now),
                    subTitle = "Block Time",
                    modifier = Modifier
                        .weight(1f),
                )

                CardVerticalView(
                    title = block.txHashes.size.toString(),
                    subTitle = "Transactions",
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }
        if (block.txHashes.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Transactions of block:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(block.txHashes) { txHash ->
                CardView(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navBackStackEntry?.savedStateHandle?.set(
                            "transaction",
                            TransactionDetailsParameter.TransactionHash(txHash.hashID)
                        )
                        navController.navigate("transaction_details")
                    }
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = block.header.height)

                            Text(text = "•")

                            Text(text = block.header.time.timeAgoString(now = now))
                        }

                        MiddleText(
                            text = txHash.hashID.uppercase(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}