package com.example.notes.presentation.editorScreen

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
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.data.model.Note
import com.example.notes.ui.theme.DarkGray
import com.example.notes.ui.theme.DarkGray2
import com.example.notes.ui.theme.VeryDarkGray

class EditorScreen(private val noteViewModel: NoteViewModel) :Screen {
    @Composable
    override fun Content() {
        EditorScreenDesign()
    }
    @Composable
    fun EditorScreenDesign(){
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val title = remember { mutableStateOf("") }
        val content = remember { mutableStateOf("") }
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
                    if (title.value != "" || content.value != ""){
                        noteViewModel.insertNote(Note(title = title.value, content = content.value, isFavourite = false))
                        onSaveNote(context = context)
                    }else
                        Toast.makeText(context,"Note is Empty",Toast.LENGTH_SHORT).show()
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
            LazyColumn()
            {
                item { CustomBasicTextField(title)
                    CustomBasicTextField2(content) }
            }
        }
    }
    @Composable
    fun CustomBasicTextField(title: MutableState<String>) {
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
    fun CustomBasicTextField2(content: MutableState<String>) {
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
        Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
    }

}