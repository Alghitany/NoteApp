package com.example.notes.presentation.homeScreenEmpty

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.data.model.Note
import com.example.notes.presentation.SwipeToDeleteContainer
import com.example.notes.presentation.editorScreen.EditorScreen
import com.example.notes.presentation.favouriteScreen.FavoriteScreen
import com.example.notes.presentation.noteDetailsScreen.NoteDetailsScreen
import com.example.notes.presentation.searchingNoteEmptyScreen.SearchingNoteScreenEmpty
import com.example.notes.ui.theme.DarkGray
import com.example.notes.ui.theme.LightGray2
import com.example.notes.ui.theme.PaleCyan
import com.example.notes.ui.theme.PaleRed
import com.example.notes.ui.theme.PaleViolet
import com.example.notes.ui.theme.VeryDarkGray
import com.example.notes.ui.theme.VeryLightMagenta
import com.example.notes.ui.theme.VeryLightYellow
import com.example.notes.ui.theme.VerySoftLimeGreen

class HomeScreenEmpty(private val noteViewModel: NoteViewModel) : Screen{

    @Composable
    override fun Content() {
        Design()
    }
    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun Design(){
        val navigator = LocalNavigator.currentOrThrow
        val showInfoDialog = remember { mutableStateOf(false) }
        val backgroundColor = remember { mutableStateOf(DarkGray) }
        val selectedIndex = remember { mutableIntStateOf(0) }
        val notes = noteViewModel.allNotes.collectAsState()
       if (notes.value?.isEmpty() == true){EmptyHomeScreen(navigator,showInfoDialog,backgroundColor)}
         else{ HomeScreen(navigator, showInfoDialog,backgroundColor,selectedIndex,notes)}
    }
    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun HomeScreen(
        navigator: Navigator,
        showInfoDialog: MutableState<Boolean>,
        backgroundColor: MutableState<Color>,
        selectedIndex: MutableState<Int>,
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
            Box(modifier = Modifier
                .fillMaxSize(),
                ){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor.value)) {
                    item {
                        Row (modifier = Modifier.fillMaxWidth()) {
                            Text(text = stringResource(id = R.string.name),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(start = 24.dp, top = 47.dp))
                            Spacer(modifier = Modifier.width(120.dp))
                            Button(onClick = {
                                navigator.push(SearchingNoteScreenEmpty(noteViewModel))
                            },
                                colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
                                modifier = Modifier
                                    .padding(top = 51.dp, end = 21.dp)
                                    .height(50.dp)
                                    .width(50.dp),
                                shape = RoundedCornerShape(15.dp),
                                contentPadding = PaddingValues(13.dp),
                                elevation = ButtonDefaults.buttonElevation(5.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Search,
                                    contentDescription = "Search Button",
                                    modifier = Modifier.size(24.dp))
                            }
                            Button(onClick = {
                                showInfoDialog.value = true
                                backgroundColor.value = LightGray2
                            },
                                colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
                                modifier = Modifier
                                    .padding(top = 51.dp)
                                    .height(50.dp)
                                    .width(50.dp),
                                shape = RoundedCornerShape(15.dp),
                                contentPadding = PaddingValues(13.dp),
                                elevation = ButtonDefaults.buttonElevation(5.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Info,
                                    contentDescription = "Info Button",
                                    modifier = Modifier.size(24.dp))
                            }
                            if (showInfoDialog.value) {
                                StatusDialog(
                                    onDismissRequest = {
                                        showInfoDialog.value= false
                                        backgroundColor.value = DarkGray
                                    }
                                )
                                backgroundColor.value = LightGray2
                            }

                        }
                        Spacer(modifier = Modifier.height(37.dp)) }
                    items(notes.value?: emptyList(), key = {it.id}){note->
                        SwipeToDeleteContainer(item = note, onDelete = {
                            noteViewModel.deleteNote(note)
                        }) {
                            Card (modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(start = 24.5.dp, end = 24.5.dp, bottom = 24.5.dp)
                                .clickable {
                                    navigator.push(NoteDetailsScreen(note, noteViewModel))
                                },
                                shape = RoundedCornerShape(10.dp),) {
                                Column (modifier = Modifier
                                    .fillMaxSize()
                                    .background(colorList.random())){
                                    Icon(
                                        modifier = Modifier
                                            .clickable {
                                                noteViewModel.updateNote(note.copy(isFavourite = !note.isFavourite))
                                            }
                                            .align(Alignment.End)
                                            .size(27.dp)
                                            .padding(end = 5.dp, top = 5.dp),
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = if (note.isFavourite) VeryDarkGray else  Color.White)
                                    Text(text = if(note.title== "") stringResource(id = R.string.empty_title) else note.title,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 25.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 10.dp, end = 10.dp,bottom = 10.dp))

                                }
                            }
                        }
                        }
                }
                Button(onClick = {
                    navigator.push(EditorScreen(noteViewModel))
                },
                    modifier = Modifier
                        .padding(start = 290.dp, top = 600.dp, end = 35.dp)
                        .height(70.dp)
                        .width(70.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor.value),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),

                    )
                {
                    Icon(imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Button",
                        modifier = Modifier.size(40.dp))
                }
                Surface (modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .align(Alignment.BottomCenter),
                    color = VeryDarkGray.copy(alpha = .1f),
                    shape = RoundedCornerShape(20.dp)){
                    BottomAppBar(containerColor = VeryDarkGray.copy(alpha = .1f),
                        modifier = Modifier.fillMaxSize()) {
                        IconButton(modifier = Modifier.weight(.5f),
                            onClick = {
                                selectedIndex.value = 0 }) {
                            Icon(modifier = Modifier.size(50.dp),
                                imageVector = Icons.Filled.Home,
                                contentDescription =null,
                                tint = if(selectedIndex.value==0) Color.Black else  Color.White )
                        }
                        IconButton(modifier = Modifier.weight(.5f),
                            onClick = { selectedIndex.value = 1
                                navigator.push(FavoriteScreen(selectedIndex.value,noteViewModel))}) {
                            Icon(modifier = Modifier.size(50.dp),
                                imageVector = Icons.Default.Favorite,
                                contentDescription =null,
                                tint = if(selectedIndex.value==1) Color.Black else  Color.White )
                        }
                    }
                }
            }

    }
    @Composable
    fun EmptyHomeScreen(
        navigator: Navigator,
        showInfoDialog: MutableState<Boolean>,
        backgroundColor: MutableState<Color>
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value)) {
            Row (modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 24.dp, top = 47.dp))
                Spacer(modifier = Modifier.width(128.dp))
                Button(onClick = {
                    navigator.push(SearchingNoteScreenEmpty(noteViewModel))
                },
                    colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
                    modifier = Modifier
                        .padding(top = 51.dp, end = 21.dp)
                        .height(50.dp)
                        .width(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    contentPadding = PaddingValues(13.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search Button",
                        modifier = Modifier.size(24.dp))
                }
                Button(onClick = {
                    showInfoDialog.value = true
                    backgroundColor.value = LightGray2
                },
                    colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
                    modifier = Modifier
                        .padding(top = 51.dp)
                        .height(50.dp)
                        .width(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    contentPadding = PaddingValues(13.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Info,
                        contentDescription = "Info Button",
                        modifier = Modifier.size(24.dp))
                }
                if (showInfoDialog.value) {
                    StatusDialog(
                        onDismissRequest = {
                            showInfoDialog.value= false
                            backgroundColor.value = DarkGray
                        }
                    )
                    backgroundColor.value = LightGray2
                }
            }
            Spacer(modifier = Modifier.height(182.dp))
            Image(painter = painterResource(id = R.drawable.image1),
                contentDescription = "Make First Note",
                modifier = Modifier
                    .height(286.73.dp)
                    .width(350.dp)
                    .align(Alignment.CenterHorizontally))
            Text(text = stringResource(id = R.string.home_screen_empty_main_text),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 6.27.dp))
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {
                navigator.push(EditorScreen(noteViewModel))
            },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 35.dp)
                    .height(70.dp)
                    .width(70.dp),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor.value),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),

                )
            {
                Icon(imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Button",
                    modifier = Modifier.size(40.dp))
            }

        }
    }
    @Composable
    fun StatusDialog(onDismissRequest: () -> Unit) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Surface(modifier = Modifier
                .height(236.dp)
                .width(330.dp),
                color = DarkGray,
                shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(top = 38.dp, start = 28.dp)) {
                    Text(text = stringResource(id = R.string.alert_dialog_line1),
                        style = MaterialTheme.typography.labelSmall)
                    Text(text = stringResource(id = R.string.alert_dialog_line2),
                        style = MaterialTheme.typography.labelSmall)
                    Text(text = stringResource(id = R.string.alert_dialog_line3),
                        style = MaterialTheme.typography.labelSmall)
                    Text(text = stringResource(id = R.string.alert_dialog_line4),
                        style = MaterialTheme.typography.labelSmall)
                    Text(text = stringResource(id = R.string.alert_dialog_line5),
                        style = MaterialTheme.typography.labelSmall)
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 38.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom) {
                Text(text = stringResource(id = R.string.alert_dialog_line6),
                    style = MaterialTheme.typography.labelSmall,
                )
             }
            }
            }
        }}