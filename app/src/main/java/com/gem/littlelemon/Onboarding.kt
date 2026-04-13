package com.gem.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gem.littlelemon.ui.theme.Highlight1
import com.gem.littlelemon.ui.theme.Highlight2
import com.gem.littlelemon.ui.theme.Karla
import com.gem.littlelemon.ui.theme.LittleLemonTheme
import com.gem.littlelemon.ui.theme.Primary1
import com.gem.littlelemon.ui.theme.Primary2

@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current

    // Form state - persisted across recompositions with remember + mutableStateOf
    var firstName by remember { mutableStateOf("") }
    var lastName  by remember { mutableStateOf("") }
    var email     by remember { mutableStateOf("") }

    // Feedback message shown in the Snackbar after the Register button is tapped
    var message   by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Highlight1),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ---- Header: logo ------------------------------------------------
        Box(
            modifier            = Modifier.fillMaxWidth().height(80.dp),
            contentAlignment    = Alignment.Center
        ) {
            Image(
                painter            = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon logo",
                modifier           = Modifier.height(50.dp),
                contentScale       = ContentScale.Fit
            )
        }

        // ---- Hero banner -------------------------------------------------
        Box(
            modifier            = Modifier.fillMaxWidth().background(Primary1).padding(vertical = 32.dp),
            contentAlignment    = Alignment.Center
        ) {
            Text(
                text      = "Let's get to know you",
                style     = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                color     = Highlight1,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text     = "Personal information",
            style    = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color    = Highlight2,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- Input fields ------------------------------------------------
        LemonTextField(
            value         = firstName,
            onValueChange = { firstName = it },
            label         = "First name"
        )
        LemonTextField(
            value         = lastName,
            onValueChange = { lastName = it },
            label         = "Last name"
        )
        LemonTextField(
            value         = email,
            onValueChange = { email = it },
            label         = "Email"
        )

        Spacer(modifier = Modifier.height(32.dp))
         
        // ---- Register button ---------------------------------------------
        Button(
            onClick = {
                message = if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    "Registration unsuccessful. Please enter all data."
                } else {
                    saveUserToPrefs(context, firstName, lastName, email)
                    navController.navigate(Home.route) {
                        // Remove Onboarding from the back stack so Back from Home exits the app
                        popUpTo(Onboarding.route) { inclusive = true }
                    }
                    "Registration successful!"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            shape  = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary2,
                contentColor   = Highlight2
            )
        ) {
            Text(
                text  = "Register",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
        }

        // ---- Feedback snackbar -------------------------------------------
        if (message.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Snackbar(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = message, fontFamily = Karla)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// Saves the three registration fields to SharedPreferences.
private fun saveUserToPrefs(
    context:   Context,
    firstName: String,
    lastName:  String,
    email:     String
) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(KEY_FIRST, firstName)
        .putString(KEY_LAST,  lastName)
        .putString(KEY_EMAIL, email)
        .apply()
}

// Reusable styled text field matching the Little Lemon brand.
@Composable
fun LemonTextField(
    value:         String,
    onValueChange: (String) -> Unit,
    label:         String
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label, fontFamily = Karla) },
        modifier      = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape         = RoundedCornerShape(8.dp),
        colors        = OutlinedTextFieldDefaults.colors(
            focusedBorderColor   = Primary1,
            unfocusedBorderColor = Primary1.copy(alpha = 0.5f),
            focusedLabelColor    = Primary1,
            cursorColor          = Primary1
        )
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(navController = rememberNavController())
    }
}
