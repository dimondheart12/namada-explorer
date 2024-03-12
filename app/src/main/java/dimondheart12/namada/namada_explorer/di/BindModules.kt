package dimondheart12.namada.namada_explorer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dimondheart12.namada.namada_explorer.data.TomlService
import dimondheart12.namada.namada_explorer.data.TomlServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModules {
    @Binds
    abstract fun bindTomlService(tomlService: TomlServiceImpl): TomlService
}