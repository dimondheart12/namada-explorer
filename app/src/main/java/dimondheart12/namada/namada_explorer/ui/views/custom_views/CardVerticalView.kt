package dimondheart12.namada.namada_explorer.ui.views.custom_views

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CardIcon(
    @DrawableRes val id: Int,
    val tint: Color = Color.White,
    val backgroundColor: Color,
    val size: Dp = 16.dp,
    val padding: Dp = 4.dp
)

@Composable
fun CardVerticalView(
    icon: CardIcon? = null,
    title: String,
    subTitle: String,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    spacing: Dp = 4.dp
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.Start
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(icon.backgroundColor)
                        .size(icon.size + icon.padding)
                ) {
                    Image(
                        painter = painterResource(id = icon.id),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(icon.tint),
                        modifier = Modifier
                            .size(icon.size)
                            .align(Alignment.Center)
                    )
                }
            }

            Text(
                text = title + "\n".repeat(
                    2
                ),
                maxLines = 2,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subTitle + "\n".repeat(
                    2
                ),
                maxLines = 2,
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}