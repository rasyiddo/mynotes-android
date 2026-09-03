package com.example.mynotes_android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteEditorScreen(
    initialTitle: String = "",
    initialContent: String = "",
    onBackClick: () -> Unit,
    onSaveClick: (String, String) -> Unit,
    onDeleteClick: (() -> Unit)? = null
) {

    var title by remember {
        mutableStateOf(initialTitle)
    }

    var content by remember {
        mutableStateOf(initialContent)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = if (onDeleteClick != null) {
                "Edit Note"
            } else {
                "New Note"
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =================================================
        // JUDUL
        // =================================================

        OutlinedTextField(
            value = title,

            onValueChange = {
                title = it
            },

            label = {
                Text("Judul")
            },

            modifier = Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =================================================
        // TEXT FORMATTING
        // =================================================

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    content = "**$content**"
                }
            ) {
                Text("B")
            }

            Button(
                onClick = {
                    content = "*$content*"
                }
            ) {
                Text("I")
            }

            Button(
                onClick = {
                    content = "~~$content~~"
                }
            ) {
                Text("S")
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // =================================================
        // ISI CATATAN
        // =================================================

        OutlinedTextField(
            value = content,

            onValueChange = {
                content = it
            },

            label = {
                Text("Isi catatan")
            },

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =================================================
        // BUTTON SAVE / DELETE
        // =================================================

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (onDeleteClick != null) {

                Button(
                    onClick = {
                        showDeleteDialog = true
                    },

                    modifier = Modifier.weight(1f)
                ) {

                    Text("DELETE")
                }
            }

            Button(
                onClick = {
                    onSaveClick(title, content)
                },

                modifier = Modifier.weight(1f)
            ) {

                Text("SAVE")
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {
                onBackClick()
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("KEMBALI")
        }
    }


    // =================================================
    // DELETE CONFIRMATION
    // =================================================

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Hapus catatan?")
            },

            text = {
                Text(
                    "Catatan ini akan dihapus. " +
                            "Tindakan ini tidak dapat dibatalkan."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        showDeleteDialog = false

                        onDeleteClick?.invoke()
                    }
                ) {

                    Text("HAPUS")
                }
            },

            dismissButton = {

                Button(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {

                    Text("BATAL")
                }
            }
        )
    }
}