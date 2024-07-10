package uz.mobile.footzone.android.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.androidx.compose.koinViewModel
import uz.mobile.footzone.viewmodel.main.MainViewModel

class MainScreen : Screen {

    @Composable
    override fun Content() {
        MainScreenContent(
            viewModel = koinViewModel()
        )
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    Scaffold { a ->
        Column(
            modifier
                .padding(a)
                .fillMaxSize()
        ) {
            Button(onClick = {

            }) {
                Text(text = "Press")
            }
        }
    }
}