package com.example.mynotes_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes_android.ui.theme.MynotesandroidTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MynotesandroidTheme {
                MyNotesApp()
            }
        }
    }
}


@Composable
fun MyNotesApp() {

    val navController = rememberNavController()

    var notes by remember {
        mutableStateOf(listOf<Note>())
    }

    var selectedNote by remember {
        mutableStateOf<Note?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // =========================
        // HOME
        // =========================

        composable("home") {

            Scaffold(

                topBar = {
                    MyNotesTopBar()
                },

                floatingActionButton = {

                    FloatingActionButton(
                        onClick = {
                            navController.navigate("new_note")
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah catatan"
                        )
                    }
                }

            ) { innerPadding ->

                NotesList(
                    notes = notes,
                    modifier = Modifier.padding(innerPadding),

                    onNoteClick = { note ->

                        selectedNote = note

                        navController.navigate("edit_note")
                    }
                )
            }
        }


        // =========================
        // NEW NOTE
        // =========================

        composable("new_note") {

            NoteEditorScreen(

                onBackClick = {
                    navController.popBackStack()
                },

                onSaveClick = { title, content ->

                    notes = notes + Note(
                        id = notes.size + 1,
                        title = title,
                        content = content
                    )

                    navController.popBackStack()
                }
            )
        }


        // =========================
        // EDIT NOTE
        // =========================

        composable("edit_note") {

            selectedNote?.let { note ->

                NoteEditorScreen(

                    initialTitle = note.title,

                    initialContent = note.content,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onSaveClick = { title, content ->

                        notes = notes.map {

                            if (it.id == note.id) {

                                it.copy(
                                    title = title,
                                    content = content
                                )

                            } else {

                                it
                            }
                        }

                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


@Composable
fun MyNotesTopBar() {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "MyNotes",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        IconButton(
            onClick = {
                // Nanti digunakan untuk menu
            }
        ) {

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }
    }
}


@Composable
fun NotesList(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    onNoteClick: (Note) -> Unit
) {

    Column(

        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (notes.isEmpty()) {

            Column(

                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Belum ada catatan",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Tekan tombol + untuk membuat catatan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        } else {

            notes.forEach { note ->

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNoteClick(note)
                        }
                        .padding(bottom = 12.dp)
                ) {

                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}