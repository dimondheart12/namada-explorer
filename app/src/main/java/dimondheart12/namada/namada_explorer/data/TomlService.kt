package dimondheart12.namada.namada_explorer.data

import dimondheart12.namada.namada_explorer.domain.NamadaExplorerParameters

interface TomlService {
    suspend fun readParametersTomlFile(): NamadaExplorerParameters
}