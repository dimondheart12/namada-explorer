package dimondheart12.namada.namada_explorer.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dimondheart12.namada.namada_explorer.BuildConfig
import dimondheart12.namada.namada_explorer.data.ItRedNamadaNetwork
import dimondheart12.namada.namada_explorer.data.RPCNetwork
import dimondheart12.namada.namada_explorer.data.StakePoolNetwork
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().also { interceptor ->
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Provides
    @Singleton
    fun providesRPCNetwork(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): RPCNetwork = Retrofit.Builder()
        .baseUrl("https://indexer.painhub.homes/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun providesStakepoolNetwork(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): StakePoolNetwork = Retrofit.Builder()
        .baseUrl("https://namada-explorer-api.stakepool.dev.br")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun providesItRedNetwork(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): ItRedNamadaNetwork = Retrofit.Builder()
        .baseUrl("https://it.api.namada.red/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun providesToml(): Toml = Toml()
}