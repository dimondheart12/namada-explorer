package dimondheart12.namada.namada_explorer.data

import dimondheart12.namada.namada_explorer.domain.Proposals
import retrofit2.http.GET

interface ItRedNamadaNetwork {
    @GET("api/v1/chain/governance/proposals")
    suspend fun getProposals(): Proposals
}