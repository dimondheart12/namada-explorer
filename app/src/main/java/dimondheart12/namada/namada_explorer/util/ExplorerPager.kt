package dimondheart12.namada.namada_explorer.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dimondheart12.namada.namada_explorer.domain.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.reflect.KProperty

fun <Data : Any> explorerPager(loadData: suspend (page: Int) -> Page<Data>): Pager<Int, Data> =
    Pager(
        initialKey = 1,
        config = PagingConfig(
            pageSize = 10
        ),
        pagingSourceFactory = {
            object : PagingSource<Int, Data>() {
                override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                    }
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
                    return withContext(Dispatchers.IO) {
                        try {
                            val page = params.key ?: 1
                            val responses = loadData(page)
                            LoadResult.Page(
                                data = responses.data,
                                prevKey = if (page == 0) null else page - 1,
                                nextKey = if (responses.total <= responses.data.size) null else page + 1
                            )
                        } catch (e: Exception) {
                            LoadResult.Error(e)
                        }
                    }
                }
            }
        }
    )

inline operator fun <Data : Any> Pager<Int, Data>.getValue(
    thisObj: Any?,
    property: KProperty<*>
): Flow<PagingData<Data>> {
    return this.flow
}