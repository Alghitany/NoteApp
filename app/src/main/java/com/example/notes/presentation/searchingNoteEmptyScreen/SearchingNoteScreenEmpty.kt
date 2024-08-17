package com.example.notes.presentation.searchingNoteEmptyScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.data.model.Note
import com.example.notes.presentation.SwipeToDeleteContainer
import com.example.notes.presentation.noteDetailsScreen.NoteDetailsScreen
import com.example.notes.ui.theme.LightGray3
import com.example.notes.ui.theme.PaleCyan
import com.example.notes.ui.theme.PaleRed
import com.example.notes.ui.theme.PaleViolet
import com.example.notes.ui.theme.VeryDarkGray
import com.example.notes.ui.theme.VeryLightMagenta
import com.example.notes.ui.theme.VeryLightYellow
import com.example.notes.ui.theme.VerySoftLimeGreen

class SearchingNoteScreenEmpty (private val noteViewModel: NoteViewModel): Screen {


    @Composable
    override fun Content() {
        SearchingNoteScreenDesign()
    }
    @Composable
    fun SearchingNoteScreenDesign(){
        val notes = noteViewModel.allNotes.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val isFocused = remember { mutableStateOf(false) }
        val textState = remember { mutableStateOf("") }
        Column(modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()) {
            OutlinedTextField(
                value = textState.value,
                onValueChange = {
                    textState.value = it
                },
                textStyle = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(top = 88.dp, start = 27.dp, end = 27.dp)
                    .fillMaxWidth()
                    .onFocusChanged {
                        isFocused.value = !isFocused.value
                    },
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Refresh,
                        contentDescription = "Fake Icon",
                        tint = Color.Transparent,
                        modifier = Modifier
                            .size(15.dp)
                    )
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Search Icon",
                        tint = LightGray3,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { textState.value = "" }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = VeryDarkGray,
                    unfocusedContainerColor = VeryDarkGray,
                    disabledContainerColor = VeryDarkGray,
                    errorContainerColor = Color.Transparent,
                    cursorColor = LightGray3,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,

                    ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Search),
                placeholder = {
                    Text(
                        text = if (isFocused.value) stringResource(id = R.string.search_screen_textField1)
                        else stringResource(id = R.string.search_screen_textField2),
                        color = LightGray3
                    )
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column (modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                if (isFocused.value) EmptySearchAction()
                else SearchAction(textState,navigator,notes)
            }
        }
    }
    @Composable
    fun SearchAction(
        textState: MutableState<String>,
        navigator: Navigator,
        notes: State<List<Note>?>,
    ) {
        val colorList: List<Color> = listOf(
            VeryLightMagenta,
            PaleRed,
            VerySoftLimeGreen,
            VeryLightYellow,
            PaleCyan,
            PaleViolet
        )
        if (textState.value.isNotEmpty()) {
            LazyColumn {
                items(notes.value?.filter{it.title.contains(textState.value) || it.content.contains(textState.value)}?: emptyList(), key = {it.id}) {note->
                    SwipeToDeleteContainer(item = note, onDelete = {
                        noteViewModel.deleteNote(note)
                    }) {
                        Card (modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(start = 24.5.dp, end = 24.5.dp, bottom = 24.5.dp)
                            .clickable {
                                navigator.push(NoteDetailsScreen(it, noteViewModel))
                            },
                            shape = RoundedCornerShape(10.dp),) {
                            Column (modifier = Modifier
                                .fillMaxSize()
                                .background(colorList.random())){
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            noteViewModel.updateNote(it.copy(isFavourite = !it.isFavourite))
                                        }
                                        .align(Alignment.End)
                                        .size(27.dp)
                                        .padding(end = 5.dp, top = 5.dp),
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Color.White)
                                Text(text = note.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 25.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(10.dp))
                                Text(text = note.content.filter { note.content.contains(textState.value) },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 17.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(10.dp))

                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun EmptySearchAction() {
        Image(painter = painterResource(id = R.drawable.image2),
            contentDescription = "Search Image",
            modifier = Modifier
                .height(239.1.dp)
                .width(370.dp)
                )
        Text(text = stringResource(id = R.string.search_screen_body),
            style = MaterialTheme.typography.bodySmall)
    }
}