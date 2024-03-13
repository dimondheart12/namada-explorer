package dimondheart12.namada.namada_explorer.ui.views.custom_views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

sealed class DataState<Data> {
    class Loading<Data> : DataState<Data>()
    data class Success<Data>(val data: Data) : DataState<Data>()
    class Throwable<Data>(val exception: Exception) : DataState<Data>()
}

@Composable
fun <Data> DataStateView(
    state: DataState<Data>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    section: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.(data: Data) -> Unit,
) {
    Box(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            if (header != null) {
                header()
            }
            when (state) {
                is DataState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is DataState.Success -> {
                    if (section != null) {
                        section()
                    }
                    content(state.data)
                }

                is DataState.Throwable -> {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = state.exception.toString(),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            if (footer != null) {
                footer()
            }
        }
    }
}

@Composable
fun <Data> DataStateScrollView(
    state: DataState<Data>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    section: (LazyListScope.() -> Unit)? = null,
    content: LazyListScope.(data: Data) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        if (header != null) {
            header()
        }
        when (state) {
            is DataState.Loading -> item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is DataState.Success -> {
                if (section != null) {
                    section()
                }
                content(state.data)
            }

            is DataState.Throwable -> item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = state.exception.toString(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        if (footer != null) {
            footer()
        }
    }
}

@Composable
fun <Data> DataStateListView(
    state: DataState<List<Data>>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    section: (LazyListScope.() -> Unit)? = null,
    content: @Composable LazyItemScope.(data: Data) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        if (header != null) {
            header()
        }
        when (state) {
            is DataState.Loading -> item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is DataState.Success -> {
                if (section != null) {
                    section()
                }
                items(state.data) { item ->
                    content(item)
                }
            }

            is DataState.Throwable -> item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = state.exception.toString(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        if (footer != null) {
            footer()
        }
    }
}

@Composable
fun <Data : Any> DataStateView(
    data: LazyPagingItems<Data>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: (LazyListScope.() -> Unit)? = null,
    footer: (LazyListScope.() -> Unit)? = null,
    section: (LazyListScope.() -> Unit)? = null,
    content: @Composable LazyItemScope.(data: Data) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        if (header != null) {
            header()
        }
        when (val dataState = data.loadState.refresh) {
            is LoadState.Loading -> item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is LoadState.NotLoading -> {
                if (section != null) {
                    section()
                }
                items(count = data.itemCount) { index ->
                    content(data[index]!!)
                }

                when (val loadMoreState = data.loadState.append) {
                    LoadState.Loading -> item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    is LoadState.NotLoading -> {

                    }

                    is LoadState.Error -> item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = loadMoreState.toString(),
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            is LoadState.Error -> item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = dataState.error.toString(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        if (footer != null) {
            footer()
        }
    }
}