package dimondheart12.namada.namada_explorer.ui.views.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dimondheart12.namada.namada_explorer.R
import dimondheart12.namada.namada_explorer.ui.views.pages.blocks.BlocksPage
import dimondheart12.namada.namada_explorer.ui.views.pages.home.HomePage
import dimondheart12.namada.namada_explorer.ui.views.pages.proposals.ProposalsPage
import dimondheart12.namada.namada_explorer.ui.views.pages.transactions.TransactionsPage
import dimondheart12.namada.namada_explorer.ui.views.pages.validators.ValidatorsPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavController,
) {
    var selectedTab by rememberSaveable { mutableStateOf(MainPageState.HOME) }

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (selectedTab == MainPageState.HOME) "Namada explorer" else selectedTab.title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate("parameters")
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.setting),
                                contentDescription = "Parameters"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    )
                )
            },
            bottomBar = {
                TabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    modifier = Modifier.height(52.dp)
                ) {
                    MainPageState.entries.forEach { state ->
                        Tab(
                            icon = {
                                Image(
                                    painter = painterResource(id = state.icon),
                                    contentDescription = state.title,
                                    colorFilter = ColorFilter.tint(if (state == selectedTab) Color.Black else Color.Gray),
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            text = {
                                Text(
                                    text = state.title,
                                    fontSize = 9.sp
                                )
                            },
                            selected = selectedTab == state,
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.Gray,
                            onClick = {
                                selectedTab = state
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTab) {
                    MainPageState.HOME -> HomePage()
                    MainPageState.BLOCKS -> BlocksPage(
                        navController = navController
                    )

                    MainPageState.TRANSACTIONS -> TransactionsPage(
                        navController = navController
                    )

                    MainPageState.VALIDATORS -> ValidatorsPage(
                        navController = navController
                    )

                    MainPageState.PROPOSALS -> ProposalsPage(
                        navController = navController
                    )
                }
            }
        }
    }
}