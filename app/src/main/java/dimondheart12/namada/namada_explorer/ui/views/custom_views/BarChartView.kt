package dimondheart12.namada.namada_explorer.ui.views.custom_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BarChartConfig(
    val title: String,
    val value: Long,
    val backgroundColor: Color,
    val textColor: Color,
)

@Composable
fun BarChartView(
    values: List<BarChartConfig>,
    fontSize: TextUnit = 10.sp,
    modifier: Modifier = Modifier
) {
    val maxItem = values.maxOf { it.value }
    val maxValue = maxItem * 100 / 80
    Column(
        modifier = Modifier then modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (value in values) {
            val weight = value.value.toDouble() / maxValue.toDouble()
            if (weight > 0) {
                BoxWithConstraints {
                    val boxWithConstraintsScope = this

                    Text(
                        text = value.title,
                        textAlign = TextAlign.Center,
                        color = value.textColor,
                        fontSize = fontSize,
                        maxLines = 1,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(boxWithConstraintsScope.maxHeight / 2))
                            .fillMaxWidth(weight.toFloat())
                            .background(value.backgroundColor)
                    )
                }
            }
        }
    }
}