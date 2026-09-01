package com.example.mynotes_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotes_android.ui.theme.MynotesandroidTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

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

                EmptyNotesScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        composable("new_note") {

            NoteEditorScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
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
fun EmptyNotesScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "INI MYNOTES!!!",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Tekan tombol + untuk membuat catatan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}