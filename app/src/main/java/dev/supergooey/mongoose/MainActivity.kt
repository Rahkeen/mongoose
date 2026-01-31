package dev.supergooey.mongoose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.supergooey.mongoose.navigation.AppNavigation
import dev.supergooey.mongoose.ui.theme.MongooseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MongooseTheme {
                AppNavigation()
            }
        }
    }
}
