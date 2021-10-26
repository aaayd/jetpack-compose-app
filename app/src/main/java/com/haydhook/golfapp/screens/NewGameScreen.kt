package com.haydhook.golfapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.haydhook.golfapp.utils.showErrorDialog

private val showPlayerInput = mutableStateOf(false)
private var enabledEndGame = mutableStateOf(false)
val mappedPlayerNames = hashMapOf<Int, String>()
var pubHoleCount = 0

@Composable
fun NewGameScreen() {
    val focusManager = LocalFocusManager.current
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

        OutlinedTextField(
            value = playerCount,
            onValueChange = { playerCount = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            singleLine = true,
            label = { Text("Player Count") }
        )

        OutlinedTextField(
            value = holeCount,
            onValueChange = { holeCount = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            singleLine = true,
            label = { Text("Hole Count") }
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


            OutlinedButton(onClick = {
                if (playerCount == "" || holeCount == "") {
                    showErrorDialog.value = true
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
        ErrorDialog(title = "Error", text = "Please ensure all fields contain numbers.")
    }

    if (showPlayerInput.value) {
        InputPlayers(playerCount.toInt(), holeCount.toInt())
    }

}

@Composable
fun InputPlayers(index : Int, holeCount : Int) {
    val scrollState = rememberScrollState()

    Spacer(modifier = Modifier.height(8.dp))

    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    showPlayerInput.value = false
                },
                title = {
                    Text(text = "Player Setup")
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
}

@Preview(showBackground = true)
@Composable
fun NewGameScreenPreview() {
    NewGameScreen()
}
