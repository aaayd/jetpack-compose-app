package com.haydhook.golfapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun CurrentGameScreen() {
    val playerNameArray = mutableListOf<String>()
    val holeCountArray = mutableListOf<String>()

    for(i in 0 until mappedPlayerNames.values.count()) {
        playerNameArray.add(mappedPlayerNames[i].toString())
    }

    for(i in 0 until pubHoleCount) {
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

        if (mappedPlayerNames.isEmpty()) {

            Text(
                text = "No Current Games",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        } else {
            Text(
                text = "Game created with ${mappedPlayerNames.count()} players",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )

        }

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
            var cellValue by remember { mutableStateOf("TEST VAL") }
            println(item)
            println(cellValue)
            TextField(
                value = cellValue,
                onValueChange = { cellValue = it },
                singleLine =  true,
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
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }

        Table(
            columnCount = playerNameArray.count(),
            cellWidth = cellWidth,
            data = holeCountArray,
            modifier = Modifier.verticalScroll(rememberScrollState()),
            headerCellContent = headerCellTitle,
            cellContent = cellText
        )
    }
}

@Composable
fun <T> Table(
    columnCount: Int,
    cellWidth: (index: Int) -> Dp,
    data: List<T>,
    modifier: Modifier = Modifier,
    headerCellContent: @Composable (index: Int) -> Unit,
    cellContent: @Composable (index: Int, item: T) -> Unit,
) {
    Surface(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.padding(16.dp)
        ) {
            itemsIndexed((0 until columnCount).toList()) { col_index, column ->
                Column {
                    (0..data.size).forEach { index ->
                        Surface(
                            border = BorderStroke(1.dp, Color.LightGray),
                            contentColor = Color.Transparent,
                            modifier = Modifier.width(cellWidth(col_index))
                        ) {
                            if (index == 0) {
                                headerCellContent(col_index)
                            } else {
                                cellContent(col_index, data[index - 1])
                                println("col_index : $col_index   data.size_index : $index")
                            }
                        }
                    }
                }
            }

            var mapColValues = hashMapOf<String, Int>()

            itemsIndexed((0 until columnCount).toList()) { col_index, column ->
                Column {
                    (0..data.size).forEach { index ->
                        Surface(
                            border = BorderStroke(1.dp, Color.LightGray),
                            contentColor = Color.Transparent,
                            modifier = Modifier.width(cellWidth(col_index))
                        ) {
                            if (index == 0) {
                                headerCellContent(col_index)
                            } else {
                                cellContent(col_index, data[index - 1])

//                                if (!mapColValues.containsKey(col_index.toString())) {
//
//                                    mapColValues[col_index.toString()] = data[index - 1].toString().toInt()
//
//                                } else {
//
//                                    mapColValues[col_index.toString()] = mapColValues[col_index.toString()]!!.toInt() +  data[index - 1].toString().toInt()
//
//
//                                }

                                println(mapColValues)
                                println(
                                    "col_index : $col_index   data.size_index : $index cellContent : ${
                                        cellContent(
                                            col_index,
                                            data[index - 1]
                                        )
                                    }"
                                )
                            }
                        }
                    }
                }
            }
        }
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

