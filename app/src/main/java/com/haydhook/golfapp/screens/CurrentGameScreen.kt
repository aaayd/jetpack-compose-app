package com.haydhook.golfapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haydhook.golfapp.utils.GolfTable


@Composable
fun CurrentGameScreen() {
    var gameText by remember { mutableStateOf("No Current Games") }
    var playerNameArray = mutableListOf<String>()
    val holeCountArray = mutableListOf<String>()

    for (i in 0 until mappedPlayerNames.values.count()) {
        playerNameArray.add(mappedPlayerNames[i].toString())
    }

    for (i in 0 until pubHoleCount + 1) {
        holeCountArray.add(i.toString())
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Current Game",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        gameText = if (mappedPlayerNames.isEmpty()) {
            "No Current Games"
        } else {
            playerNameArray = (mutableListOf("Hole") + playerNameArray) as MutableList<String>
            "Game created with ${mappedPlayerNames.count()} players & ${holeCountArray.count()} holes"
        }

        Text(
            text = gameText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 15.sp
        )


        val cellWidth: (Int) -> Dp = { index ->
            when (index) {
                else -> 150.dp
            }
        }

        val headerCellTitle: @Composable (Int) -> Unit = { index ->
            val headerValue = playerNameArray[index]

            Text(
                text = headerValue,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Black,
            )
        }

        val cellText: @Composable (Int, String) -> Unit = { index, item ->
            var cellValue by remember { mutableStateOf("") }
            var enabled by remember { mutableStateOf(true) }
            if (index == 0) {
                cellValue = ((item.toInt()) + 1).toString()

                if (item == holeCountArray.last().toString()) {
                    cellValue = "Totals"
                }
                enabled = false

            } else if (item == holeCountArray.last().toString() && index != 0) {
                cellValue = "0"
                enabled = false

            }

            TextField(
                value = cellValue,
                onValueChange = { cellValue = it },
                singleLine = true,
                enabled = enabled,

                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }

        GolfTable(
            columnCount = playerNameArray.count(),
            cellWidth = cellWidth,
            data = holeCountArray,
            modifier = Modifier.verticalScroll(rememberScrollState()),
            headerCellContent = headerCellTitle,
            cellContent = cellText
        )
    }
}




//LazyColumn {
//    itemsIndexed(holeCountArray) {index , row ->
//        LazyRow {
//            itemsIndexed(playerNameArray) { index, column ->
//                Text(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .background(Color.Red)
//                        .width(100.dp)
//                        .height(100.dp),
//                    textAlign = TextAlign.Center,
//                    color = Color.White,
//                    text = "$column"
//                )
//            }
//        }
//
//    }
//}

