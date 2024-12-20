package com.example.myapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R

@Composable
fun OptionalBar(
    text: String,
    height: Dp = 50.dp,
    backgroundColor: Color = Color.White,
    onclick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onclick()
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(0.8f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow",
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )
}
