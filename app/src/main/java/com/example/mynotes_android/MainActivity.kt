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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes_android.data.AppDatabase
import com.example.mynotes_android.data.NoteEntity
import com.example.mynotes_android.data.TodoEntity
import com.example.mynotes_android.ui.theme.MynotesandroidTheme
import com.example.mynotes_android.ui.theme.ThemeOption
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MynotesandroidTheme {
                MyNotesApp(database)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNotesApp(
    database: AppDatabase
) {

    val navController = rememberNavController()

    val scope = rememberCoroutineScope()

    // ==========================================
    // STATE DATA
    // ==========================================

    var notes by remember {
        mutableStateOf(listOf<Note>())
    }

    var todos by remember {
        mutableStateOf(listOf<Todo>())
    }

    var selectedNote by remember {
        mutableStateOf<Note?>(null)
    }

    var selectedTodo by remember {
        mutableStateOf<Todo?>(null)
    }

    var showAddDialog by remember {
        mutableStateOf(false)
    }

    // ==========================================
    // SEARCH
    // ==========================================

    var searchText by remember {
        mutableStateOf("")
    }

    // ==========================================
    // THEME
    // ==========================================

    var selectedTheme by remember {
        mutableStateOf(ThemeOption.WHITE)
    }


    // ==========================================
    // LOAD DATA DARI ROOM
    // ==========================================

    LaunchedEffect(Unit) {

        notes = database
            .noteDao()
            .getAllNotes()
            .first()
            .map {
                Note(
                    id = it.id,
                    title = it.title,
                    content = it.content
                )
            }

        todos = database
            .todoDao()
            .getAllTodos()
            .first()
            .map {
                Todo(
                    id = it.id,
                    title = it.title,
                    isCompleted = it.isCompleted
                )
            }
    }


    // ==========================================
    // FILTER SEARCH
    // ==========================================

    val filteredNotes = notes.filter { note ->

        searchText.isBlank() ||
                note.title.contains(
                    searchText,
                    ignoreCase = true
                ) ||
                note.content.contains(
                    searchText,
                    ignoreCase = true
                )
    }


    val filteredTodos = todos.filter { todo ->

        searchText.isBlank() ||
                todo.title.contains(
                    searchText,
                    ignoreCase = true
                )
    }


    // ==========================================
    // THEME WRAPPER
    // ==========================================

    MynotesandroidTheme(
        themeOption = selectedTheme
    ) {

        NavHost(
            navController = navController,
            startDestination = "home"
        ) {

            // ==========================================
            // HOME
            // ==========================================

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

                ) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {

                        // ==========================================
                        // SEARCH BAR
                        // ==========================================

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            placeholder = {
                                Text("Cari note atau todo...")
                            }
                        )


                        // ==========================================
                        // THEME PICKER
                        // ==========================================

                        ThemePicker(
                            selectedTheme = selectedTheme,
                            onThemeSelected = {
                                selectedTheme = it
                            }
                        )


                        // ==========================================
                        // NOTE + TODO
                        // ==========================================

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            NotesList(
                                notes = filteredNotes,
                                modifier = Modifier.weight(1f),
                                onNoteClick = { note ->

                                    selectedNote = note

                                    navController.navigate(
                                        "edit_note"
                                    )
                                }
                            )


                            TodoList(
                                todos = filteredTodos,
                                modifier = Modifier.weight(1f),

                                onTodoChecked = { todo ->

                                    val updatedTodo =
                                        todo.copy(
                                            isCompleted =
                                                !todo.isCompleted
                                        )

                                    todos = todos.map {

                                        if (it.id == todo.id) {
                                            updatedTodo
                                        } else {
                                            it
                                        }
                                    }

                                    scope.launch {

                                        database
                                            .todoDao()
                                            .updateTodo(
                                                TodoEntity(
                                                    id = updatedTodo.id,
                                                    title = updatedTodo.title,
                                                    isCompleted =
                                                        updatedTodo.isCompleted
                                                )
                                            )
                                    }
                                },

                                onTodoClick = { todo ->

                                    selectedTodo = todo

                                    navController.navigate(
                                        "edit_todo"
                                    )
                                }
                            )
                        }
                    }
                }


                // ==========================================
                // ADD DIALOG
                // ==========================================

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

                                TextButton(
                                    onClick = {

                                        showAddDialog = false

                                        navController.navigate(
                                            "new_note"
                                        )
                                    },

                                    modifier = Modifier.fillMaxWidth()

                                ) {
                                    Text("📝 Note")
                                }


                                TextButton(
                                    onClick = {

                                        showAddDialog = false

                                        navController.navigate(
                                            "new_todo"
                                        )
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


            // ==========================================
            // NEW NOTE
            // ==========================================

            composable("new_note") {

                NoteEditorScreen(

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onSaveClick = { title, content ->

                        scope.launch {

                            database
                                .noteDao()
                                .insertNote(
                                    NoteEntity(
                                        title = title,
                                        content = content
                                    )
                                )

                            notes = database
                                .noteDao()
                                .getAllNotes()
                                .first()
                                .map {
                                    Note(
                                        id = it.id,
                                        title = it.title,
                                        content = it.content
                                    )
                                }
                        }

                        navController.popBackStack()
                    }
                )
            }


            // ==========================================
            // EDIT NOTE
            // ==========================================

            composable("edit_note") {

                selectedNote?.let { note ->

                    NoteEditorScreen(

                        initialTitle = note.title,

                        initialContent = note.content,

                        onBackClick = {
                            navController.popBackStack()
                        },

                        onSaveClick = { title, content ->

                            scope.launch {

                                database
                                    .noteDao()
                                    .updateNote(
                                        NoteEntity(
                                            id = note.id,
                                            title = title,
                                            content = content
                                        )
                                    )

                                notes = database
                                    .noteDao()
                                    .getAllNotes()
                                    .first()
                                    .map {
                                        Note(
                                            id = it.id,
                                            title = it.title,
                                            content = it.content
                                        )
                                    }
                            }

                            navController.popBackStack()
                        },

                        onDeleteClick = {

                            scope.launch {

                                database
                                    .noteDao()
                                    .deleteNote(
                                        NoteEntity(
                                            id = note.id,
                                            title = note.title,
                                            content = note.content
                                        )
                                    )

                                notes = database
                                    .noteDao()
                                    .getAllNotes()
                                    .first()
                                    .map {
                                        Note(
                                            id = it.id,
                                            title = it.title,
                                            content = it.content
                                        )
                                    }
                            }

                            navController.popBackStack()
                        }
                    )
                }
            }


            // ==========================================
            // NEW TODO
            // ==========================================

            composable("new_todo") {

                TodoEditorScreen(

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onSaveClick = { title ->

                        scope.launch {

                            database
                                .todoDao()
                                .insertTodo(
                                    TodoEntity(
                                        title = title
                                    )
                                )

                            todos = database
                                .todoDao()
                                .getAllTodos()
                                .first()
                                .map {
                                    Todo(
                                        id = it.id,
                                        title = it.title,
                                        isCompleted =
                                            it.isCompleted
                                    )
                                }
                        }

                        navController.popBackStack()
                    }
                )
            }


            // ==========================================
            // EDIT TODO
            // ==========================================

            composable("edit_todo") {

                selectedTodo?.let { todo ->

                    TodoEditorScreen(

                        initialTitle = todo.title,

                        onBackClick = {
                            navController.popBackStack()
                        },

                        onSaveClick = { title ->

                            scope.launch {

                                database
                                    .todoDao()
                                    .updateTodo(
                                        TodoEntity(
                                            id = todo.id,
                                            title = title,
                                            isCompleted =
                                                todo.isCompleted
                                        )
                                    )

                                todos = database
                                    .todoDao()
                                    .getAllTodos()
                                    .first()
                                    .map {
                                        Todo(
                                            id = it.id,
                                            title = it.title,
                                            isCompleted =
                                                it.isCompleted
                                        )
                                    }
                            }

                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}


// =====================================================
// TOP BAR
// =====================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNotesTopBar() {

    TopAppBar(
        title = {
            Text("MyNotes")
        }
    )
}


// =====================================================
// THEME PICKER
// =====================================================

@Composable
fun ThemePicker(
    selectedTheme: ThemeOption,
    onThemeSelected: (ThemeOption) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {

        Text(
            text = "Tema",
            style = MaterialTheme.typography.titleMedium
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            ThemeButton(
                emoji = "🌸",
                name = "Pink",
                selected =
                    selectedTheme == ThemeOption.PINK,
                onClick = {
                    onThemeSelected(
                        ThemeOption.PINK
                    )
                }
            )


            ThemeButton(
                emoji = "🌿",
                name = "Sage",
                selected =
                    selectedTheme == ThemeOption.SAGE,
                onClick = {
                    onThemeSelected(
                        ThemeOption.SAGE
                    )
                }
            )


            ThemeButton(
                emoji = "💜",
                name = "Ungu",
                selected =
                    selectedTheme == ThemeOption.PURPLE,
                onClick = {
                    onThemeSelected(
                        ThemeOption.PURPLE
                    )
                }
            )


            ThemeButton(
                emoji = "🖤",
                name = "Hitam",
                selected =
                    selectedTheme == ThemeOption.BLACK,
                onClick = {
                    onThemeSelected(
                        ThemeOption.BLACK
                    )
                }
            )


            ThemeButton(
                emoji = "🤍",
                name = "Putih",
                selected =
                    selectedTheme == ThemeOption.WHITE,
                onClick = {
                    onThemeSelected(
                        ThemeOption.WHITE
                    )
                }
            )
        }
    }
}


// =====================================================
// THEME BUTTON
// =====================================================

@Composable
fun ThemeButton(
    emoji: String,
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally,

        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(4.dp)
    ) {

        Text(
            text = emoji,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall
        )

        if (selected) {

            Text(
                text = "✓",
                style = MaterialTheme.typography.labelSmall
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
                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = "Belum ada catatan",
                    style =
                        MaterialTheme.typography.titleLarge
                )

                Text(
                    text =
                        "Tekan tombol + untuk membuat catatan",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
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
                        style =
                            MaterialTheme.typography.titleMedium
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

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(
                    text = "Belum ada tugas",
                    style =
                        MaterialTheme.typography.titleLarge
                )

                Text(
                    text =
                        "Tekan tombol + untuk membuat tugas",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
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
                        .padding(vertical = 4.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = todo.isCompleted,

                        onCheckedChange = {
                            onTodoChecked(todo)
                        }
                    )


                    Text(
                        text = todo.title,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}