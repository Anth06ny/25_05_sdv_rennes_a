package com.amonteiro.a25_05_sdv_rennes_a.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amonteiro.a25_05_sdv_rennes_a.ui.theme._25_05_sdv_rennes_aTheme
import com.amonteiro.a25_05_sdv_rennes_a.viewmodel.MainViewModel

@Preview(showBackground = true, showSystemUi = true, name = "1")
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES, name = "2")
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    _25_05_sdv_rennes_aTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = MainViewModel()) {
    Column(modifier = modifier) {
        println("SearchScreen()")
        Text(
            text = "Text1", fontSize = 20.sp,
            modifier = Modifier
                .background(Color.Yellow)
                .padding(3.dp)
                .background(Color.Red)
        )
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2", fontSize = 14.sp)

        val list = mainViewModel.dataList.collectAsStateWithLifecycle().value

        repeat(list.size) {
            PictureRowItem(list[it].title)
        }
    }
}

@Composable
fun PictureRowItem(text: String) {
    Text(text = text, fontSize = 20.sp)
}