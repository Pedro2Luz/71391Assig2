package com.stu71391.simplenavigation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stu71391.simplenavigation.components.SearchBar
import com.stu71391.simplenavigation.ui.theme.SimpleNavigationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            val currentDestination = navBackStackEntry?.destination
            val searchQuery = remember { mutableStateOf(TextFieldValue()) }
            val screens = listOf(Routes.FirstScreen, Routes.SearchScreen)
            val showBottomBar = currentDestination?.route in screens.map { it.route }

            SimpleNavigationTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {

                    AnimatedVisibility(visible = currentDestination?.route == Routes.FirstScreen.route) {
                        LargeTopAppBar(
                            title = {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                        ,
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = null,)
                                    },
                            scrollBehavior = scrollBehavior,
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ))
                    }

                    AnimatedContent(
                        targetState = currentDestination?.route == Routes.SearchScreen.route,
                        label = ""
                    ) {
                        if (it) {

                            SearchBar(query = searchQuery.value, onQueryChange = { textField ->
                                searchQuery.value = textField
                            }, onBackClick = {
                                navController.popBackStack()
                            }, onClearClick = {
                                searchQuery.value = TextFieldValue()
                            })
                        }
                    }
                }, bottomBar = {
                    AnimatedVisibility(
                        visible = showBottomBar,
                        enter = slideInVertically(animationSpec = tween(400)) { it },
                        exit = slideOutVertically(animationSpec = tween(400)) { it },
                    ) {
                        NavigationBar {
                            screens.forEach { screen ->
                                val selected =
                                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                                NavigationBarItem(selected = selected, onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true

                                    }
                                }, icon = {
                                    screen.icon?.let {
                                        Icon(imageVector = it, contentDescription = null)
                                    }
                                })
                            }
                        }

                    }
                }) { paddingValues ->
                    AppNavigation(
                        searchQuery = searchQuery.value.text,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}

