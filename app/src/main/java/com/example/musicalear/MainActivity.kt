package com.example.musicalear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicalear.ui.theme.MusicalEarTheme

enum class PageState {
    HOME,
    LISTEN,
    RECOGNITION
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicalEarTheme {
                var pageState: PageState by remember {
                    mutableStateOf((PageState.HOME))
                }

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding: PaddingValues ->
                    when (pageState){
                        PageState.HOME -> HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onGoToPage = {pageState = it}
                        )

                        PageState.LISTEN -> ListenScreen(
                            modifier = Modifier.padding(innerPadding),
                            onBack = {pageState = PageState.HOME}
                        )

                        PageState.RECOGNITION -> RecognitionScreen(
                            modifier = Modifier.padding(innerPadding),
                            onBack = {pageState = PageState.HOME}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    onGoToPage: (PageState) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Hello Clovis",
                modifier=modifier)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier.padding(end = 16.dp, top = 8.dp)
            ) {
                Text("Parametre")
            }
        }

        Text("Travaille ton oreille")

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {onGoToPage(PageState.LISTEN)}) {
                Text("Ecoute de note")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {onGoToPage(PageState.RECOGNITION)}) {
                Text("Reconnaissance")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}) {
                Text("Justesse/not yet ready")
            }
        }
    }
}

@Composable
fun ListenScreen(
    modifier: Modifier,
    onBack: () -> Unit
){
    Column(modifier = modifier)
    {
        BackButton(onBack)

        Text("Mode écoute de note",
            modifier = modifier)
    }
}

@Composable
fun RecognitionScreen(
    modifier: Modifier,
    onBack: () -> Unit
){
    Column(modifier = modifier)
    {
        BackButton(onBack)

        Text("Mode Reconnaissance de note",
            modifier = modifier
        )
    }
}

@Composable
fun BackButton(
    onBack: () -> Unit
){
    Button(onClick = onBack) {
        Text("Retour")
    }
}