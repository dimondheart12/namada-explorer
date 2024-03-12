package dimondheart12.namada.namada_explorer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dimondheart12.namada.namada_explorer.domain.Proposals
import dimondheart12.namada.namada_explorer.domain.Validators
import dimondheart12.namada.namada_explorer.ui.theme.NamadaexplorerTheme
import dimondheart12.namada.namada_explorer.ui.views.pages.block_details.BlockDetailParameter
import dimondheart12.namada.namada_explorer.ui.views.pages.block_details.BlockDetailsContentView
import dimondheart12.namada.namada_explorer.ui.views.pages.main.MainPage
import dimondheart12.namada.namada_explorer.ui.views.pages.parameters.ParametersPage
import dimondheart12.namada.namada_explorer.ui.views.pages.proposal_details.ProposalDetailsPage
import dimondheart12.namada.namada_explorer.ui.views.pages.transation_details.TransactionDetailsPage
import dimondheart12.namada.namada_explorer.ui.views.pages.transation_details.TransactionDetailsParameter
import dimondheart12.namada.namada_explorer.ui.views.pages.validator_details.ValidatorsDetailPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NamadaexplorerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainPage(
                            navController = navController,
                        )
                    }
                    composable("block_details") {
                        val parameter =
                            navController.previousBackStackEntry?.savedStateHandle?.get<BlockDetailParameter>(
                                "block"
                            )
                        if (parameter != null) {
                            BlockDetailsContentView(
                                navController = navController,
                                parameter = parameter
                            )
                        }
                    }
                    composable("transaction_details") {
                        val parameter =
                            navController.previousBackStackEntry?.savedStateHandle?.get<TransactionDetailsParameter>(
                                "transaction"
                            )
                        if (parameter != null) {
                            TransactionDetailsPage(
                                navController = navController,
                                parameter = parameter
                            )
                        }
                    }
                    composable("validator_details") {
                        val validator =
                            navController.previousBackStackEntry?.savedStateHandle?.get<Validators.Validator>(
                                "validator"
                            )
                        if (validator != null) {
                            ValidatorsDetailPage(
                                navController = navController,
                                validator = validator
                            )
                        }
                    }
                    composable("proposal_details") {
                        val proposal =
                            navController.previousBackStackEntry?.savedStateHandle?.get<Proposals.Proposal>(
                                "proposal"
                            )
                        if (proposal != null) {
                            ProposalDetailsPage(
                                navController = navController,
                                proposal = proposal
                            )
                        }
                    }
                    composable("parameters") {
                        ParametersPage(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}