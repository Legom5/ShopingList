package olegator.shopinglist.about_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import olegator.shopinglist.R
import olegator.shopinglist.banner.Banner
import olegator.shopinglist.ui.theme.BlueLight
import olegator.shopinglist.ui.theme.GreyLight

@Preview(showBackground = true)
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "logo",
            modifier = Modifier.size(200.dp),
            tint = Color.Unspecified
        )

        Text(
            text = stringResource(id = R.string.about_1),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.about_2),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.about_3),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.about_4),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.about_5),
            textAlign = TextAlign.Center
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Banner(id = R.string.banner_about)
    }

}