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

    // Menyimpan isi judul
    var title by remember {
        mutableStateOf(initialTitle)
    }

    // Menyimpan isi catatan
    var content by remember {
        mutableStateOf(initialContent)
    }

    // Menentukan apakah AlertDialog DELETE ditampilkan
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // =========================
        // JUDUL
        // =========================

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


        // =========================
        // INPUT JUDUL
        // =========================

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


        // =========================
        // INPUT ISI CATATAN
        // =========================

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


        // =========================
        // TOMBOL
        // =========================

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // =========================
            // TOMBOL DELETE
            // =========================

            if (onDeleteClick != null) {

                Button(
                    onClick = {
                        showDeleteDialog = true
                    },

                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "DELETE"
                    )
                }
            }


            // =========================
            // TOMBOL SAVE
            // =========================

            Button(
                onClick = {
                    onSaveClick(
                        title,
                        content
                    )
                },

                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "SAVE"
                )
            }
        }


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // =========================
        // TOMBOL BACK
        // =========================

        Button(
            onClick = {
                onBackClick()
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "KEMBALI"
            )
        }
    }


    // =========================
    // DELETE CONFIRMATION
    // =========================

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text(
                    text = "Hapus catatan?"
                )
            },

            text = {
                Text(
                    text = "Catatan ini akan dihapus. Tindakan ini tidak dapat dibatalkan."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        // Tutup dialog
                        showDeleteDialog = false

                        // Jalankan fungsi delete
                        onDeleteClick?.invoke()
                    }
                ) {

                    Text(
                        text = "HAPUS"
                    )
                }
            },

            dismissButton = {

                Button(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {

                    Text(
                        text = "BATAL"
                    )
                }
            }
        )
    }
}