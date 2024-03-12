package dimondheart12.namada.namada_explorer.data

import dimondheart12.namada.namada_explorer.domain.Block
import dimondheart12.namada.namada_explorer.domain.Page
import dimondheart12.namada.namada_explorer.domain.Transaction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RPCNetwork {
    @GET("block")
    suspend fun getBlocks(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Page<Block>

    @GET("tx")
    suspend fun getTransactions(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Page<Transaction>

    @GET("block/hash/{id}")
    suspend fun getBlock(@Path("id") id: String): Block

    @GET("tx/{hash}")
    suspend fun getTransaction(@Path("hash") hash: String): Transaction
}