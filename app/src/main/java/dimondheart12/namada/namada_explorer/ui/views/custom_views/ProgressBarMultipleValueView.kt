package dimondheart12.namada.namada_explorer.ui.views.custom_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ProgressBarConfig(
    val title: String,
    val value: Long,
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
fun ProgressBarMultipleValueView(
    values: List<ProgressBarConfig>,
    fontSize: TextUnit = 10.sp,
    modifier: Modifier = Modifier
) {
    val sum = values.sumOf { it.value }
    Row(
        modifier = Modifier.fillMaxHeight() then modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (value in values) {
            val weight = value.value.toDouble() / sum.toDouble()
            if (weight > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(weight.toFloat())
                        .background(value.backgroundColor)
                ) {
                    Text(
                        text = value.title,
                        textAlign = TextAlign.Center,
                        color = value.textColor,
                        fontSize = fontSize,
                        maxLines = 1,
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}
