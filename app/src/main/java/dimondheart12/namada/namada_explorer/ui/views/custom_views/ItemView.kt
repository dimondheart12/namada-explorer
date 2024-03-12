@file:OptIn(ExperimentalMaterial3Api::class)

package dimondheart12.namada.namada_explorer.ui.views.custom_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ItemView(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            onClick = onClick,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    radius: Dp = 8.dp,
    containerColor: Color = Color.White,
    paddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    if (onClick == null) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(radius),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )
        ) {
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                content()
            }
        }
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(radius),
            onClick = onClick,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )
        ) {
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                content()
            }
        }
    }
}