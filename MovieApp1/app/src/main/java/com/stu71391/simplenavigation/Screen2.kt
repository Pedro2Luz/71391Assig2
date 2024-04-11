package com.stu71391.simplenavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stu71391.simplenavigation.models.Movie

@Composable
fun Screen2(
    movie: Movie, paddingValues: PaddingValues
) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        item {
            Screen2Content(movie = movie)
        }
    }
}

@Composable
private fun Screen2Content(movie: Movie) {


    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Box(
            modifier = Modifier
                .height(400.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Black, Color.Blue), startY = 0f, endY = 500f
                        ),
                    ),
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    painter = painterResource(id = movie.posterResId),
                    contentScale = ContentScale.Crop,
                    alpha = 0.8f,
                    contentDescription = "Movie Poster"
                )
            }


                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(8.dp))
                        .size(width = 160.dp, height = 250.dp),
                    painter = painterResource(id = movie.posterResId),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Movie Poster"
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp),
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Synopsis:",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier =  Modifier.padding(top = 5.dp),
                text = movie.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )

            TextWithIcon(
                modifier = Modifier.padding(top = 20.dp),
                icon = Icons.Outlined.PersonOutline,
                text = movie.starring
            )
            TextWithIcon(
                icon = Icons.Outlined.AccessTime,
                text = movie.runningTime)


            SelectSeats(
                modifier = Modifier.padding(top = 15.dp), movie = movie
            )
        }

    }
}

@Composable
fun TextWithIcon(
    icon: ImageVector, text: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
         Icon(
             imageVector = icon,
             tint = MaterialTheme.colorScheme.primary,
             contentDescription = null)

        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun SelectSeats(
    movie: Movie, modifier: Modifier = Modifier
) {
    var selectedSeatNumbers by remember {
        mutableIntStateOf(0)
    }
    val buttonIncreaseNumberSeatsIsEnabled = selectedSeatNumbers < movie.maxSeats
    val buttonDecreaseNumberSeatsIsEnabled = selectedSeatNumbers > 0


    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(
            text = "Select Seats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(enabled = buttonDecreaseNumberSeatsIsEnabled, onClick = {

                --selectedSeatNumbers

            }) {
                Text(text = "-")
            }

            Text(text = selectedSeatNumbers.toString(), color = MaterialTheme.colorScheme.primary)

            IconButton(enabled = buttonIncreaseNumberSeatsIsEnabled, onClick = {

                ++selectedSeatNumbers
            }) {
                Text(text = "+")
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Outlined.Chair,
                contentDescription = null)
            Text(
                modifier = Modifier.padding(horizontal = 5.dp), text = movie.maxSeats.toString()
            )
        }
    }

}