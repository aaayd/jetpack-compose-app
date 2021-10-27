package com.haydhook.golfapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haydhook.golfapp.utils.GolfTable
import com.haydhook.golfapp.utils.regexDigit


@Composable
fun CurrentGameScreen() {
    val maxCharLength = 2
    var gameText by remember { mutableStateOf("No Current Games") }
    var playerNameArray = mutableListOf<String>()
    val holeCountArray = mutableListOf<String>()
    var sumHashMap = hashMapOf<String, String>()

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
                0 -> 75.dp
                else -> 125.dp
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

        val cellText: @Composable (Int, String) -> Unit = { column_index, item ->
            var cellValue by remember { mutableStateOf("") }
            var enabled by remember { mutableStateOf(true) }
            if (column_index == 0) {
                cellValue = ((item.toInt()) + 1).toString()

                if (item == holeCountArray.last().toString()) {
                    cellValue = ""
//                    cellValue = "Totals"
                }
                enabled = false

            } else if (item == holeCountArray.last().toString() && column_index != 0) {
                sumHashMap[column_index.toString()] = column_index.toString()
                cellValue = sumHashMap[column_index.toString()].toString()
                enabled = false
            }
            sumHashMap[column_index.toString()] = column_index.toString()

            TextField(
                value = cellValue,
                onValueChange = {
                    cellValue = regexDigit.replace(it, "")
                    if (it.length >= maxCharLength) {
                        cellValue = regexDigit.replace(it.dropLast(it.length -  maxCharLength), "")
                    }
                    sumHashMap[column_index.toString()] = ((sumHashMap[column_index.toString()]!!.toInt()) + cellValue.toInt()).toString()
                    cellValue = sumHashMap[column_index.toString()].toString()
                    cellValue = it

                    println("sumHashMap : $sumHashMap    cellValue : $cellValue    it : $it")
                                },
                singleLine = true,
                enabled = enabled,
                modifier = Modifier
                    .padding(8.dp)
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

