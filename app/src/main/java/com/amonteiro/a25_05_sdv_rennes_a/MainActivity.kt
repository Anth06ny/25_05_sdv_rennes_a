package com.amonteiro.a25_05_sdv_rennes_a

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amonteiro.a25_05_sdv_rennes_a.ui.theme._25_05_sdv_rennes_aTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _25_05_sdv_rennes_aTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Greeting(
                        name = "from android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

//showSystemUi Affiche le reste de l'écran
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark",
    showSystemUi = true
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    _25_05_sdv_rennes_aTheme {
        Greeting("from preview")
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark",
    showSystemUi = true
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview2() {
    _25_05_sdv_rennes_aTheme {
        Greeting("from preview2")
    }
}