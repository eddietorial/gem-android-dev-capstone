package com.gem.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.gem.littlelemon.ui.theme.Highlight1
import com.gem.littlelemon.ui.theme.Highlight2
import com.gem.littlelemon.ui.theme.Karla
import com.gem.littlelemon.ui.theme.LittleLemonTheme
import com.gem.littlelemon.ui.theme.Primary1
import com.gem.littlelemon.ui.theme.Primary2
 
@Composable
fun Home(navController: NavHostController) {
    val context  = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    var searchPhrase     by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    // observeAsState means the list auto-refreshes whenever Room writes new data
    val allMenuItems by database.menuItemDao().getAllMenuItems().observeAsState(emptyList())

    val categories = allMenuItems.map { it.category }.distinct().sorted()

    val displayedItems = allMenuItems
        .filter { searchPhrase.isBlank() || it.title.contains(searchPhrase, ignoreCase = true) }
        .filter { selectedCategory.isBlank() || it.category == selectedCategory }

    Column(modifier = Modifier.fillMaxSize().background(Highlight1)) {

        // ---- Header ------------------------------------------------------
        HomeHeader(navController = navController)
        HorizontalDivider(color = Primary1.copy(alpha = 0.15f))

        // ---- Scrollable body (hero + breakdown + items) ------------------
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                HeroSection(
                    searchPhrase = searchPhrase,
                    onSearchChange = { searchPhrase = it }
                )
            }

            item {
                MenuBreakdown(
                    categories       = categories,
                    selectedCategory = selectedCategory,
                    onCategoryToggle = { cat ->
                        selectedCategory = if (selectedCategory == cat) "" else cat
                    }
                )
            }

            item { HorizontalDivider(color = Primary1.copy(alpha = 0.15f)) }

            items(displayedItems, key = { it.id }) { item ->
                MenuItem(item)
                HorizontalDivider(color = Primary1.copy(alpha = 0.1f))
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// ---- Header ---------------------------------------------------------------

@Composable
fun HomeHeader(navController: NavHostController) {
    Box(
        modifier         = Modifier.fillMaxWidth().height(80.dp).padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Logo centered
        Image(
            painter            = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon logo",
            modifier           = Modifier.height(50.dp),
            contentScale       = ContentScale.Fit
        )
        // Profile icon on the right - navigates to Profile screen
        Image(
            painter            = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier           = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable { navController.navigate(Profile.route) },
            contentScale       = ContentScale.Crop
        )
    }
}

// ---- Hero section ---------------------------------------------------------

@Composable
fun HeroSection(searchPhrase: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary1)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text       = "Little Lemon",
            fontFamily = com.gem.littlelemon.ui.theme.MarkaziText,
            fontSize   = 52.sp,
            color      = Primary2
        )
        Text(
            text       = "Chicago",
            fontFamily = com.gem.littlelemon.ui.theme.MarkaziText,
            fontSize   = 36.sp,
            color      = Highlight1
        )

        Row(
            modifier            = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment   = Alignment.CenterVertically
        ) {
            Text(
                text       = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                fontFamily = Karla,
                fontSize   = 14.sp,
                color      = Highlight1,
                modifier   = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter            = painterResource(id = R.drawable.hero_image),
                contentDescription = "Restaurant hero image",
                modifier           = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale       = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search bar
        OutlinedTextField(
            value         = searchPhrase,
            onValueChange = onSearchChange,
            placeholder   = { Text("Enter search phrase", fontFamily = Karla) },
            modifier      = Modifier.fillMaxWidth(),
            singleLine    = true,
            shape         = RoundedCornerShape(8.dp),
            leadingIcon   = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Highlight2) },
            colors        = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor  = Highlight1,
                focusedContainerColor    = Highlight1,
                focusedBorderColor       = Primary2,
                unfocusedBorderColor     = Highlight1
            )
        )
    }
}

// ---- Menu breakdown (category filter chips) --------------------------------

@Composable
fun MenuBreakdown(
    categories:       List<String>,
    selectedCategory: String,
    onCategoryToggle: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text       = "ORDER FOR DELIVERY!",
            fontFamily = Karla,
            fontWeight = FontWeight.ExtraBold,
            fontSize   = 18.sp,
            color      = Highlight2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick  = { onCategoryToggle(cat) },
                    label    = {
                        Text(
                            text       = cat.replaceFirstChar { it.uppercase() },
                            fontFamily = Karla,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor    = Primary1,
                        selectedLabelColor        = Highlight1,
                        containerColor            = Highlight1,
                        labelColor                = Primary1
                    )
                )
            }
        }
    }
}

// ---- Single menu item row -------------------------------------------------

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = item.title,
                fontFamily = Karla,
                fontWeight = FontWeight.Bold,
                fontSize   = 16.sp,
                color      = Highlight2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text       = item.description,
                fontFamily = Karla,
                fontSize   = 14.sp,
                color      = Highlight2.copy(alpha = 0.7f),
                maxLines   = 2
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text       = "$${item.price}",
                fontFamily = Karla,
                fontWeight = FontWeight.SemiBold,
                fontSize   = 14.sp,
                color      = Primary1
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        GlideImage(
            model              = item.image,
            contentDescription = item.title,
            modifier           = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale       = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home(navController = rememberNavController())
    }
}
