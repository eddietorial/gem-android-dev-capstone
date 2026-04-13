package com.gem.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gem.littlelemon.ui.theme.Highlight1
import com.gem.littlelemon.ui.theme.Highlight2
import com.gem.littlelemon.ui.theme.LittleLemonTheme
import com.gem.littlelemon.ui.theme.Primary1
import com.gem.littlelemon.ui.theme.Primary2

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val prefs   = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val firstName = prefs.getString(KEY_FIRST, "") ?: ""
    val lastName  = prefs.getString(KEY_LAST,  "") ?: ""
    val email     = prefs.getString(KEY_EMAIL, "") ?: ""
 
    Column(
        modifier            = Modifier.fillMaxSize().background(Highlight1),
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

        HorizontalDivider(color = Primary1.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text     = "Profile information",
            style    = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color    = Highlight2,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- User data ---------------------------------------------------
        ProfileField(label = "First name", value = firstName)
        ProfileField(label = "Last name",  value = lastName)
        ProfileField(label = "Email",      value = email)

        Spacer(modifier = Modifier.weight(1f))

        // ---- Log out button ----------------------------------------------
        Button(
            onClick = {
                prefs.edit().clear().apply()
                navController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .height(50.dp),
            shape  = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary2,
                contentColor   = Highlight2
            )
        ) {
            Text(
                text  = "Log out",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
        }
    }
}

// A labelled read-only display row.
@Composable
private fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text  = label,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = Primary1
        )
        Text(
            text     = value,
            style    = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            color    = Highlight2,
            modifier = Modifier.padding(top = 4.dp)
        )
        HorizontalDivider(
            modifier  = Modifier.padding(top = 8.dp),
            color     = Primary1.copy(alpha = 0.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        Profile(navController = rememberNavController())
    }
}
