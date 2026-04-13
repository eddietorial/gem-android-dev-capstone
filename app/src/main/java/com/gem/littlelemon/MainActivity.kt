package com.gem.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.gem.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    // Ktor client configured with the JSON serialization plugin.
    // ignoreUnknownKeys = true makes the client tolerant of any extra fields
    // the API might add in the future without crashing deserialization.
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Fetch and cache menu items in the background.
        // IO dispatcher keeps network work off the main thread.
        val database = AppDatabase.getDatabase(applicationContext)
        lifecycleScope.launch(Dispatchers.IO) {
            val items = fetchMenu()
            database.menuItemDao().insertAll(items.map { it.toMenuItemEntity() })
        }

        setContent {
            LittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Navigation(
                        navController = navController,
                        // Note: Modifier.padding(innerPadding) is applied inside Navigation
                        // via the Scaffold padding so the bottom nav bar does not overlap.
                    )
                }
            }
        }
    }

    // Performs the GET request and deserializes the response into MenuNetwork.
    // Returns an empty list if the request fails (e.g. no network on first run).
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return try {
            httpClient
                .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body<MenuNetwork>()
                .menu
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        httpClient.close()
    }
}
