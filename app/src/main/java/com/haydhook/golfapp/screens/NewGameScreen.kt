package com.haydhook.golfapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haydhook.golfapp.ui.theme.clrError
import com.haydhook.golfapp.ui.theme.clrSuccess
import com.haydhook.golfapp.utils.ErrorDialog
import com.haydhook.golfapp.utils.regexDigit
import com.haydhook.golfapp.utils.showErrorDialog

private const val maxPlayerCount = 8
private const val maxHoleCount = 16

private val showPlayerInput = mutableStateOf(false)
private var enabledEndGame = mutableStateOf(false)
private var enabledCreateNewGame = mutableStateOf(false)

val mappedPlayerNames = hashMapOf<Int, String>()
val mappedText = hashMapOf<String, String>()

var errorTitle = "Error"
var errorText = ""
var pubHoleCount = 0



fun validate(text : String): Array<String> {
    var errorText = ""

    if (regexDigit.replace(text, "").length != text.length) {
        errorText = "Please remove all none-digit characters."

    } else if (text == "") {
        errorText = "Please ensure text is not empty."
    }

    if (errorText != "")
        return arrayOf("true", errorText)
    return arrayOf("", errorText)

}

@Composable 
fun ValidatedTextField(text : String, label : String, varName : String) {
    var _text  by remember { mutableStateOf(text) }
    var _errorText  by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    Column {
        OutlinedTextField(
            value = _text,
            onValueChange = {
                var validation = validate(it)
                isError = validation[0].toBoolean()
                enabledCreateNewGame.value = !validation[0].toBoolean()

                _errorText = validation[1]
                _text = it

                mappedText[varName] = _text
                println("holeCountDict : ${mappedText}")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            trailingIcon = {
                if (isError)
                    Icon(
                        Icons.Filled.Warning,
                        "error",
                        tint = MaterialTheme.colors.error)
            },
            isError = isError,
            singleLine = true,
            label = { Text(label) }
        )

        if (isError) {
            Text(
                text = _errorText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun NewGameScreen() {
    var playerCount  by remember { mutableStateOf("") }
    var holeCount by remember { mutableStateOf("") }

    if (mappedPlayerNames.isEmpty()) {
        enabledEndGame.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {

        ValidatedTextField(
            text = playerCount,
            label = "Player Count",
            varName = "playerCount"
        )

        ValidatedTextField(
            text = holeCount,
            label = "Hole Count" ,
            varName = "holeCount"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row() {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = clrError),
                enabled = enabledEndGame.value,
                onClick = {
                    mappedPlayerNames.clear()
                    pubHoleCount = 0
                    showPlayerInput.value = false
                    enabledEndGame.value = false

                }) {
                Text("End Game")
            }
            Spacer(modifier = Modifier.width(8.dp))


            OutlinedButton(

                enabled = enabledCreateNewGame.value,
                onClick = {
                    playerCount = mappedText["playerCount"].toString()
                    holeCount = mappedText["holeCount"].toString()

                if (playerCount == "" || holeCount == "") {
                    showErrorDialog.value = true
                    errorText = "Please ensure all fields contain numbers."

                } else if (playerCount.toInt() > maxPlayerCount) {
                    showErrorDialog.value = true
                    errorText = "Max players hit. Please choose up to $maxPlayerCount players"

                } else if (holeCount.toInt() > maxHoleCount) {
                    showErrorDialog.value = true
                    errorText = "Max hole count hit. Please choose up to $maxHoleCount holes"

                } else {
                    mappedPlayerNames.clear()
                    showPlayerInput.value = true
                }

            }) {
                Text("Create New Game")
            }
        }


        }

    if (showErrorDialog.value) {
        ErrorDialog(title = errorTitle, text = errorText)
    }

    if (showPlayerInput.value) {
        InputPlayers(playerCount.toInt(), holeCount.toInt())
    }

}

@Composable
fun InputPlayers(index : Int, holeCount : Int) {
    val scrollState = rememberScrollState()
    Spacer(modifier = Modifier.padding(top = 8.dp))

        Column{
            AlertDialog(
                modifier = Modifier.padding(top = 16.dp),
                onDismissRequest = {
                    showPlayerInput.value = false
                },
                title = {
                    Text(text = "Player Setup")
                },
                text = {
                    Column(
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .verticalScroll(scrollState)
                    ) {
                        repeat(index) { counter ->
                            var playerName by remember { mutableStateOf("") }
                            mappedPlayerNames[counter] = playerName
                            playerName = mappedPlayerNames[counter].toString()

                            OutlinedTextField(
                                value = playerName,
                                onValueChange = { playerName = it },
                                singleLine = true,
                                label = { Text("Player ${counter+1} Name") }
                            )
                        }
                    }
                },

                dismissButton = {
                    Button(
                        colors= ButtonDefaults.buttonColors(backgroundColor = clrError),
                        onClick = {
                            mappedPlayerNames.clear()
                            showPlayerInput.value = false
                        }) {
                        Text("Cancel")
                    }

                },

                confirmButton = {
                    Button(
                        colors= ButtonDefaults.buttonColors(backgroundColor = clrSuccess),
                        onClick = {

                            showPlayerInput.value = false
                            enabledEndGame = mutableStateOf(true)

                            pubHoleCount = holeCount
                            for ((key, value) in mappedPlayerNames) {
                                if (value.isNullOrEmpty()) {
                                    mappedPlayerNames[key] = "Player ${key + 1}"
                                }
                            }

                        }) {
                        Text("Done")
                    }
                },
            )
        }
}

@Preview(showBackground = true)
@Composable
fun NewGameScreenPreview() {
    NewGameScreen()
}
