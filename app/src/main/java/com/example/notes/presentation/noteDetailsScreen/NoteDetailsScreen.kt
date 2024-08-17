package com.example.notes.presentation.noteDetailsScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.data.model.Note
import com.example.notes.ui.theme.DarkGray
import com.example.notes.ui.theme.DarkGray2
import com.example.notes.ui.theme.VeryDarkGray

class NoteDetailsScreen(private val note: Note,private val noteViewModel: NoteViewModel) :Screen {
    @Composable
    override fun Content() {
        NoteDetailsScreenDesign()
    }
    @Composable
    fun NoteDetailsScreenDesign() {
        val navigator = LocalNavigator.currentOrThrow
        val isEditMode = remember {mutableStateOf(false)}
        val isReadonly = remember { mutableStateOf(true) }
        val title = remember { mutableStateOf(note.title) }
        val content = remember { mutableStateOf(note.content) }
        Column(modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()) {
            Row (modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    navigator.pop()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
                    modifier = Modifier
                        .padding(top = 53.dp, start = 23.dp)
                        .height(50.dp)
                        .width(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back Button",
                        modifier = Modifier.size(40.dp))
                }
                if (isEditMode.value) EditClicked(navigator,isEditMode,isReadonly,title,content)
                else EditNotClicked(isEditMode,isReadonly)
            }
            LazyColumn()
            {
                item { CustomBasicTextField(isReadonly,title)
                    CustomBasicTextField2(isReadonly,content) }
            }
        }
    }
    @Composable
    fun EditClicked(
        navigator: Navigator,
        isEditMode: MutableState<Boolean>,
        isReadonly: MutableState<Boolean>,
        title: MutableState<String>,
        content: MutableState<String>
    ) {
        val context = LocalContext.current
        Spacer(modifier = Modifier.width(197.dp))
        Button(onClick = {
            //TODO//
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
            Icon(painter = painterResource(id = R.drawable.ic_visibility_icon),
                contentDescription = "Visible Button",
                modifier = Modifier.size(24.dp))
        }
        Button(onClick = {

            if(title.value=="" && content.value == ""){
                Toast.makeText(context,"Note Deleted",Toast.LENGTH_SHORT).show()
                noteViewModel.deleteNote(note.copy())
                navigator.pop()
            }else{
                noteViewModel.updateNote(
                    note.copy(
                            title = title.value,
                            content = content.value,
                            isFavourite = false
                        )
                    )
                    isEditMode.value = false
                    onSaveNote(context = context)
                    isReadonly.value = true
            }

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
            Icon(painter = painterResource(id = R.drawable.ic_save_icon),
                contentDescription = "Save Button",
                modifier = Modifier.size(24.dp))
        }
    }
    @Composable
    fun EditNotClicked(isEditMode: MutableState<Boolean>, isReadonly: MutableState<Boolean>) {
        Spacer(modifier = Modifier.width(250.dp))
        Button(onClick = {
            isEditMode.value = true
            isReadonly.value = false
        },
            colors = ButtonDefaults.buttonColors(containerColor = VeryDarkGray),
            modifier = Modifier
                .padding(top = 53.dp)
                .height(50.dp)
                .width(50.dp),
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp)
        ) {
            Icon(imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit Button",
                modifier = Modifier.size(27.dp))
        }
    }
    @Composable
    fun CustomBasicTextField(isReadonly: MutableState<Boolean>, title: MutableState<String>) {
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 24.dp, bottom = 10.dp)
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            if (title.value.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.editor_screen_body),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 48.sp,
                    color = DarkGray2,
                )
            }

            BasicTextField(
                readOnly = isReadonly.value,
                value = title.value,
                onValueChange = { title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                cursorBrush = SolidColor(Color.White),
            )
        }

    }

    @Composable
    fun CustomBasicTextField2(isReadonly: MutableState<Boolean>, content: MutableState<String>) {
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 24.dp)
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            if (content.value.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.editor_screen_body2),
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 23.sp,
                    color = DarkGray2,
                )
            }

            BasicTextField(
                readOnly = isReadonly.value,
                value = content.value,
                onValueChange = { content.value = it },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.displayMedium,
                cursorBrush = SolidColor(Color.White),
            )
        }
    }
    private fun onSaveNote(context: Context){
        Toast.makeText(context,"Note Updated",Toast.LENGTH_SHORT).show()
    }
}