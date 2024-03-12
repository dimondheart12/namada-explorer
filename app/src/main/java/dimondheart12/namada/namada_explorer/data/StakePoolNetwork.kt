package dimondheart12.namada.namada_explorer.data

import dimondheart12.namada.namada_explorer.domain.BlockSignature
import dimondheart12.namada.namada_explorer.domain.Validators
import retrofit2.http.GET
import retrofit2.http.Path

interface StakePoolNetwork {
    @GET("node/validators/list")
    suspend fun getValidators(): Validators

    @GET("node/validators/validator/{address}/latestSignatures")
    suspend fun getBlockSignatures(@Path("address") address: String): List<BlockSignature>
}