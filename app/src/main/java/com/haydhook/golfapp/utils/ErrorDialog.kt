package com.haydhook.golfapp.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.haydhook.golfapp.ui.theme.clrBackgroundError

var showErrorDialog = mutableStateOf(false)
@Composable
fun ErrorDialog(title : String, text : String) {
        Column (
        ){
            showErrorDialog.value = true

            if (showErrorDialog.value) {

                AlertDialog(
                    backgroundColor = clrBackgroundError,
//                    modifier = Modifier
//                        .background(color = clrBackgroundError),

                    onDismissRequest = {
                        showErrorDialog.value = false
                    },
                    title = {
                        Text(text = title,
                            fontSize = 20.sp
                        )
                    },
                    text = {
                        Text(
                            text = text,
                            fontSize = 15.sp
                        )
                    },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White,
                            ),
                                onClick = {
                                showErrorDialog.value = false
                            }) {
                            Text("OK")
                        }
                    },
                )
            }
        }

    }