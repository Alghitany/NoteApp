package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.Navigator
import com.example.notes.presentation.homeScreenEmpty.HomeScreenEmpty
import com.example.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            NotesTheme {
                val noteViewModel: NoteViewModel = hiltViewModel()
                Navigator(screen = HomeScreenEmpty(noteViewModel))
            }
        }
    }
}