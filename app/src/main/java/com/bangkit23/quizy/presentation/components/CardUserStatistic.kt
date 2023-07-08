package com.bangkit23.quizy.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bangkit23.quizy.R
import com.bangkit23.quizy.presentation.ui.theme.QuizyTheme

@Composable
fun CardUserStatistic(
    ranking: Int,
    points: Int,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
        ) {
            val (rankingStatistic, divider, pointStatistic) = createRefs()
            UserStatisticItem(
                title = "Ranking",
                value = ranking,
                image = ImageVector.vectorResource(R.drawable.ic_ranking),
                modifier = Modifier
                    .constrainAs(rankingStatistic) {
                        top.linkTo(parent.top)
                        linkTo(parent.start, divider.start, bias = 0f)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Box(
                modifier = Modifier
                    .width(DividerDefaults.Thickness)
                    .background(color = DividerDefaults.color)
                    .constrainAs(divider) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            )
            UserStatisticItem(
                title = "Points",
                value = points,
                image = ImageVector.vectorResource(R.drawable.ic_coin),
                modifier = Modifier
                    .constrainAs(pointStatistic) {
                        linkTo(divider.end, parent.end, startMargin = 16.dp, bias = 0f)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
fun UserStatisticItem(
    title: String,
    value: Int,
    image: ImageVector,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier) {
        val (imageIcon, titleText, valueText) = createRefs()
        Image(
            imageVector = image,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
                .constrainAs(imageIcon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(imageIcon.top)
                    bottom.linkTo(valueText.top)
                    start.linkTo(imageIcon.end, margin = 16.dp)
                }
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .constrainAs(valueText) {
                    top.linkTo(titleText.bottom)
                    bottom.linkTo(imageIcon.bottom)
                    start.linkTo(imageIcon.end, margin = 16.dp)
                }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun CardUserStatisticPreview() {
    QuizyTheme {
        CardUserStatistic(
            ranking = 20,
            points = 2000,
        )
    }
}