package com.example.mynotes_android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
        mutableStateOf(
            TextFieldValue(initialContent)
        )
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var previousContent by remember {
        mutableStateOf(initialContent)
    }

    var canUndo by remember {
        mutableStateOf(false)
    }

    var canRedo by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // =========================
        // TOP BAR
        // =========================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    onBackClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali"
                )
            }

            Text(
                text = if (onDeleteClick != null) {
                    "Edit Note"
                } else {
                    "New Note"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

            if (onDeleteClick != null) {

                IconButton(
                    onClick = {
                        showDeleteDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =========================
        // TITLE
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
            modifier = Modifier.height(12.dp)
        )

        // =========================
        // TOOLBAR
        // =========================

        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // BOLD
                TextButton(
                    onClick = {

                        previousContent = content.text
                        canUndo = true
                        canRedo = false

                        content = applyFormat(
                            content,
                            "**",
                            "**"
                        )
                    }
                ) {
                    Text(
                        text = "B",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // ITALIC
                TextButton(
                    onClick = {

                        previousContent = content.text
                        canUndo = true
                        canRedo = false

                        content = applyFormat(
                            content,
                            "*",
                            "*"
                        )
                    }
                ) {
                    Text(
                        text = "I",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // STRIKETHROUGH
                TextButton(
                    onClick = {

                        previousContent = content.text
                        canUndo = true
                        canRedo = false

                        content = applyFormat(
                            content,
                            "~~",
                            "~~"
                        )
                    }
                ) {
                    Text(
                        text = "S",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // PEMBATAS
                Text(
                    text = "|",
                    color = MaterialTheme.colorScheme.outline
                )

                // UNDO
                IconButton(
                    onClick = {

                        if (canUndo) {

                            val currentContent =
                                content.text

                            content = TextFieldValue(
                                text = previousContent
                            )

                            previousContent =
                                currentContent

                            canRedo = true
                            canUndo = false
                        }
                    },
                    enabled = canUndo
                ) {
                    Icon(
                        imageVector = Icons.Default.Undo,
                        contentDescription = "Undo"
                    )
                }

                // REDO
                IconButton(
                    onClick = {

                        if (canRedo) {

                            val currentContent =
                                content.text

                            content = TextFieldValue(
                                text = previousContent
                            )

                            previousContent =
                                currentContent

                            canUndo = true
                            canRedo = false
                        }
                    },
                    enabled = canRedo
                ) {
                    Icon(
                        imageVector = Icons.Default.Redo,
                        contentDescription = "Redo"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // =========================
        // CONTENT EDITOR
        // =========================

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 1.dp
        ) {

            BasicTextField(
                value = content,
                onValueChange = {

                    previousContent =
                        content.text

                    content = it

                    canUndo = true
                    canRedo = false
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                decorationBox = { innerTextField ->

                    if (content.text.isEmpty()) {

                        Text(
                            text = "Tulis catatanmu di sini...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    innerTextField()
                }
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // =========================
        // SAVE BUTTON
        // =========================

        TextButton(
            onClick = {

                onSaveClick(
                    title,
                    content.text
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "SIMPAN",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    // =========================
    // DELETE DIALOG
    // =========================

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

                TextButton(
                    onClick = {

                        showDeleteDialog = false

                        onDeleteClick?.invoke()
                    }
                ) {
                    Text("HAPUS")
                }
            },

            dismissButton = {

                TextButton(
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


// ======================================================
// FUNGSI UNTUK FORMAT TEKS
// ======================================================

fun applyFormat(
    value: TextFieldValue,
    startMarker: String,
    endMarker: String
): TextFieldValue {

    val text = value.text

    val selectionStart =
        value.selection.start

    val selectionEnd =
        value.selection.end

    // =========================================
    // TIDAK ADA TEKS YANG DIPILIH
    // =========================================

    if (selectionStart == selectionEnd) {

        if (text.isEmpty()) {

            return value
        }

        return TextFieldValue(
            text = startMarker +
                    text +
                    endMarker,

            selection = TextRange(
                startMarker.length,
                startMarker.length + text.length
            )
        )
    }

    // =========================================
    // ADA TEKS YANG DIPILIH
    // =========================================

    val selectedText =
        text.substring(
            selectionStart,
            selectionEnd
        )

    val formattedText =
        startMarker +
                selectedText +
                endMarker

    val newText =
        text.substring(
            0,
            selectionStart
        ) +
                formattedText +
                text.substring(
                    selectionEnd
                )

    val newSelectionStart =
        selectionStart + startMarker.length

    val newSelectionEnd =
        newSelectionStart + selectedText.length

    return TextFieldValue(
        text = newText,
        selection = TextRange(
            newSelectionStart,
            newSelectionEnd
        )
    )
}