package com.gem.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Shared-preferences key constants used across the app.
const val PREFS_NAME   = "LittleLemon"
const val KEY_FIRST    = "firstName"
const val KEY_LAST     = "lastName"
const val KEY_EMAIL    = "email"

/**
 * The NavHost that wires all three screens together. 
 *
 * Start destination logic:
 *   - If firstName is already saved the user previously registered,
 *     so skip Onboarding and go straight to Home.
 *   - Otherwise show Onboarding first.
 */
@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current
    val prefs   = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val isLoggedIn = prefs.getString(KEY_FIRST, "").isNullOrBlank().not()

    NavHost(
        navController    = navController,
        startDestination = if (isLoggedIn) Home.route else Onboarding.route
    ) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController = navController)
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }
    }
}
