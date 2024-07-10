package uz.mobile.footzone.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import uz.mobile.footzone.android.presentation.screens.auth.RegistrationScreen
import uz.mobile.footzone.android.presentation.screens.main.MainScreen
import uz.mobile.footzone.android.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator(screen = MainScreen())
                }
            }
        }
    }
}