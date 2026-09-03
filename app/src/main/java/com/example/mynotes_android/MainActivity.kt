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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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


// =====================================================
// MAIN APP
// =====================================================

@Composable
fun MyNotesApp() {

    val navController = rememberNavController()

    // =================================================
    // DATA NOTES
    // =================================================

    var notes by remember {
        mutableStateOf(listOf<Note>())
    }

    // =================================================
    // DATA TODO
    // =================================================

    var todos by remember {
        mutableStateOf(listOf<Todo>())
    }

    // =================================================
    // SELECTED NOTE
    // =================================================

    var selectedNote by remember {
        mutableStateOf<Note?>(null)
    }

    // =================================================
    // SELECTED TODO
    // =================================================

    var selectedTodo by remember {
        mutableStateOf<Todo?>(null)
    }

    // =================================================
    // ADD DIALOG
    // =================================================

    var showAddDialog by remember {
        mutableStateOf(false)
    }


    // =================================================
    // NAVIGATION
    // =================================================

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // =================================================
        // HOME
        // =================================================

        composable("home") {

            Scaffold(

                topBar = {
                    MyNotesTopBar()
                },

                floatingActionButton = {

                    FloatingActionButton(
                        onClick = {
                            showAddDialog = true
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah"
                        )
                    }
                }

            ) { innerPadding ->

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {

                    // =================================================
                    // NOTES
                    // =================================================

                    NotesList(
                        notes = notes,

                        modifier = Modifier
                            .weight(1f),

                        onNoteClick = { note ->

                            selectedNote = note

                            navController.navigate("edit_note")
                        }
                    )


                    // =================================================
                    // TODO
                    // =================================================

                    TodoList(
                        todos = todos,

                        modifier = Modifier
                            .weight(1f),

                        onTodoChecked = { todo ->

                            todos = todos.map {

                                if (it.id == todo.id) {

                                    it.copy(
                                        isCompleted = !it.isCompleted
                                    )

                                } else {

                                    it
                                }
                            }
                        },

                        onTodoClick = { todo ->

                            selectedTodo = todo

                            navController.navigate("edit_todo")
                        }
                    )
                }
            }
        }


        // =================================================
        // NEW TODO
        // =================================================

        composable("new_todo") {

            TodoEditorScreen(

                onBackClick = {
                    navController.popBackStack()
                },

                onSaveClick = { title ->

                    todos = todos + Todo(
                        id = todos.size + 1,
                        title = title
                    )

                    navController.popBackStack()
                }
            )
        }


        // =================================================
        // EDIT TODO
        // =================================================

        composable("edit_todo") {

            selectedTodo?.let { todo ->

                TodoEditorScreen(

                    initialTitle = todo.title,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onSaveClick = { title ->

                        todos = todos.map {

                            if (it.id == todo.id) {

                                it.copy(
                                    title = title
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


        // =================================================
        // NEW NOTE
        // =================================================

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


        // =================================================
        // EDIT NOTE
        // =================================================

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
                    },

                    onDeleteClick = {

                        notes = notes.filter {
                            it.id != note.id
                        }

                        navController.popBackStack()
                    }
                )
            }
        }
    }


    // =================================================
    // ADD DIALOG
    // =================================================

    if (showAddDialog) {

        AlertDialog(

            onDismissRequest = {
                showAddDialog = false
            },

            title = {
                Text("Apa yang ingin dibuat?")
            },

            text = {

                Column {

                    // NOTE

                    TextButton(
                        onClick = {

                            showAddDialog = false

                            navController.navigate("new_note")
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("📝 Note")
                    }


                    // TODO

                    TextButton(
                        onClick = {

                            showAddDialog = false

                            navController.navigate("new_todo")
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("☑ Todo")
                    }
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showAddDialog = false
                    }
                ) {

                    Text("BATAL")
                }
            }
        )
    }
}


// =====================================================
// TOP BAR
// =====================================================

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


// =====================================================
// NOTES LIST
// =====================================================

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

        Text(
            text = "Notes",
            style = MaterialTheme.typography.titleLarge
        )

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
                        .padding(vertical = 8.dp)
                ) {

                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    FormattedText(
                        text = note.content
                    )
                }
            }
        }
    }
}


// =====================================================
// TODO LIST
// =====================================================

@Composable
fun TodoList(
    todos: List<Todo>,
    modifier: Modifier = Modifier,
    onTodoChecked: (Todo) -> Unit,
    onTodoClick: (Todo) -> Unit
) {

    Column(

        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        Text(
            text = "Todo",
            style = MaterialTheme.typography.titleLarge
        )

        if (todos.isEmpty()) {

            Column(

                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center

            ) {

                Text(
                    text = "Belum ada Todo",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Belum ada tugas yang dibuat",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        } else {

            todos.forEach { todo ->

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onTodoClick(todo)
                        }
                        .padding(vertical = 8.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Checkbox(

                        checked = todo.isCompleted,

                        onCheckedChange = {
                            onTodoChecked(todo)
                        }
                    )

                    Text(
                        text = todo.title,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}