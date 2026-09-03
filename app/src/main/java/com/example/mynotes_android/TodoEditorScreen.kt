package com.example.mynotes_android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun TodoEditorScreen(
    initialTitle: String = "",
    onBackClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {

    var title by remember {
        mutableStateOf(initialTitle)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = if (initialTitle.isEmpty()) {
                "New Todo"
            } else {
                "Edit Todo"
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = title,

            onValueChange = {
                title = it
            },

            label = {
                Text("Tugas")
            },

            modifier = Modifier.fillMaxWidth(),

            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                onSaveClick(title)
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
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
}