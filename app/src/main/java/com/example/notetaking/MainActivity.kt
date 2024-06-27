package com.example.notetaking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notetaking.ui.theme.NoteTakingTheme
import com.example.notetaking.ui_Layer.AddNoteScreen
import com.example.notetaking.ui_Layer.NoteScreen
import com.example.notetaking.ui_Layer.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteTakingTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewmodel = hiltViewModel<NoteViewModel>()
                    val state by viewmodel.state.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "note_screen"
                        ) {
                            composable("note_screen") {
                                NoteScreen(
                                    navController = navController,
                                    state = state,
                                    onEvent = viewmodel::OnEvent
                                )
                            }
                            composable("Add_note_screen"){
                                AddNoteScreen(
                                    navController = navController,
                                    state = state,
                                    OnEvent = viewmodel::OnEvent
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}