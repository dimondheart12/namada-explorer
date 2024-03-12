@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.pages.validator_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import dimondheart12.namada.namada_explorer.domain.Validators
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardVerticalView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.CardView
import dimondheart12.namada.namada_explorer.ui.views.custom_views.DataStateView
import dimondheart12.namada.namada_explorer.util.formatWithCommas

@Composable
fun ValidatorsDetailPage(
    navController: NavController,
    validator: Validators.Validator,
    viewModel: ValidatorDetailsViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    if (lifecycleState == Lifecycle.State.STARTED) {
        viewModel.loadData(address = validator.address)
    }

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
                            text = validator.moniker.ifBlank { "Validator details" },
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
                DataStateView(
                    state = viewModel.dataState,
                    header = {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            CardVerticalView(
                                title = validator.address.uppercase(),
                                subTitle = "Address",
                                modifier = Modifier.fillMaxWidth(),
                            )

                            CardVerticalView(
                                title = validator.pubKey.value.uppercase(),
                                subTitle = "Address",
                                modifier = Modifier.fillMaxWidth(),
                            )

                            CardView(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp,
                                                    color = Color.Black
                                                )
                                            ) {
                                                append(validator.votingPower.formatWithCommas)
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                    color = Color.Gray
                                                )
                                            ) {
                                                append("(")
                                                append(
                                                    "${
                                                        validator.votingPercentage.formatWithCommas(
                                                            3
                                                        )
                                                    }%"
                                                )
                                                append(")")
                                            }
                                        },
                                        maxLines = 2,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Voting power" + "\n".repeat(
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
                    }
                ) { blockSignatures ->
                    if (blockSignatures.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Transactions of block:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(5),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(blockSignatures) { blockSignature ->
                                CardView(
                                    containerColor = if (blockSignature.signStatus) {
                                        Color.Green
                                    } else {
                                        Color.Red
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier,
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = blockSignature.blockNumber.formatWithCommas,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            color = if (blockSignature.signStatus) {
                                                Color.Black
                                            } else {
                                                Color.White
                                            },
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}