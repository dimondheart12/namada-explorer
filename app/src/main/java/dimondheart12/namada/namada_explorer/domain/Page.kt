package dimondheart12.namada.namada_explorer.domain

data class Page<Data>(
    val data: List<Data>,
    val total: Int
)
