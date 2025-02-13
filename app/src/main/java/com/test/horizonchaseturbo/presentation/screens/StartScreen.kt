package com.test.horizonchaseturbo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.horizonchaseturbo.R

@Composable
fun StartScreen() {
    val whiteColor = colorResource(id = R.color.white)
    val blackColor = colorResource(id = R.color.black)
    val redColor = colorResource(id = R.color.red)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = stringResource(id = R.string.bg_dscr),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight(),
            alignment = BiasAlignment(0.33f, 0f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_dscr),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 65.dp)
            )
        }

        Column( //todo: hide when no best score stored
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .height(66.dp)
                    .width(92.dp)
                    .background(redColor)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "33",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Black,
                    color = whiteColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .height(49.dp)
                    .width(165.dp)
                    .background(redColor)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = R.string.best_score),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Black,
                    color = whiteColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Button(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(containerColor = whiteColor),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 156.dp, start = 33.dp, end = 33.dp)
                .height(60.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = stringResource(id = R.string.start_game).uppercase(),
                fontSize = 23.sp,
                color = blackColor,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
@Preview
fun Prev() {
    StartScreen()
}