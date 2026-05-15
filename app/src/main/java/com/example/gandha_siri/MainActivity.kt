package com.example.gandha_siri

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier

import androidx.core.content.ContextCompat

import com.example.gandha_siri.data.model.Tree

import com.example.gandha_siri.ui.addtree.AddTreeScreen
import com.example.gandha_siri.ui.advisor.AdvisorScreen
import com.example.gandha_siri.ui.alerts.AlertsScreen
import com.example.gandha_siri.ui.components.BottomNavBar
import com.example.gandha_siri.ui.details.TreeDetailsScreen
import com.example.gandha_siri.ui.guide.GuideScreen
import com.example.gandha_siri.ui.home.HomeScreen
import com.example.gandha_siri.ui.legal.LegalScreen
import com.example.gandha_siri.ui.map.TreeMapScreen
import com.example.gandha_siri.ui.security.SecurityScreen
import com.example.gandha_siri.ui.theme.GandhaSiriTheme
import com.example.gandha_siri.ui.tools.LanguageScreen
import com.example.gandha_siri.ui.tools.ProfileScreen
import com.example.gandha_siri.ui.tools.ToolsScreen
import com.example.gandha_siri.ui.treelist.TreeListScreen
import com.example.gandha_siri.utils.LanguageHelper

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // LOAD SAVED LANGUAGE
        val prefs = getSharedPreferences(
            "gandha_siri_prefs",
            MODE_PRIVATE
        )

        val savedLanguage =
            prefs.getString("app_language", "en") ?: "en"

        LanguageHelper.setLocale(this, savedLanguage)

        super.onCreate(savedInstanceState)

        setContent {

            GandhaSiriTheme {

                // NOTIFICATION PERMISSION
                NotificationPermission()

                var currentScreen by remember {
                    mutableStateOf("home")
                }

                MainScreen(
                    currentScreen = currentScreen,
                    onNavigate = {
                        currentScreen = it
                    }
                )
            }
        }
    }
}

@Composable
fun NotificationPermission() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val launcher =
            rememberLauncherForActivityResult(
                contract =
                    ActivityResultContracts.RequestPermission()
            ) {}

        LaunchedEffect(Unit) {

            launcher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }
}

@Composable
fun MainScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {

    var selectedTree by remember {
        mutableStateOf<Tree?>(null)
    }

    Scaffold(

        bottomBar = {

            BottomNavBar(
                currentScreen = currentScreen,
                onNavigate = onNavigate
            )
        }

    ) { paddingValues ->

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            when (currentScreen) {

                // HOME
                "home" -> HomeScreen(onNavigate)

                // ADD TREE
                "addtree" -> AddTreeScreen {
                    onNavigate("home")
                }

                // TREE LIST
                "treelist" -> TreeListScreen(

                    onBack = {
                        onNavigate("home")
                    },

                    onTreeClick = {

                        selectedTree = it
                        onNavigate("details")
                    }
                )

                // TREE DETAILS
                "details" -> {

                    if (selectedTree != null) {

                        TreeDetailsScreen(
                            tree = selectedTree!!,

                            onBack = {
                                onNavigate("treelist")
                            }
                        )

                    } else {

                        onNavigate("treelist")
                    }
                }

                // MAP
                "map" -> TreeMapScreen(

                    onBack = {
                        onNavigate("home")
                    },

                    onTreeClick = {

                        selectedTree = it
                        onNavigate("details")
                    }
                )

                // TOOLS
                "tools" -> ToolsScreen(

                    onNavigate = onNavigate,

                    onBack = {
                        onNavigate("home")
                    }
                )

                "profile" -> ProfileScreen {
                    onNavigate("tools")
                }

                "language" -> LanguageScreen {
                    onNavigate("tools")
                }

                "help" -> {
                    ToolsScreen(
                        onNavigate,
                        { onNavigate("home") }
                    )
                }

                // ADVISOR
                "advisor" -> AdvisorScreen {
                    onNavigate("home")
                }

                // SECURITY
                "security" -> SecurityScreen(
                    onBack = {
                        onNavigate("home")
                    }
                )

                // AUTO SOS
                "security_sos" -> SecurityScreen(

                    onBack = {
                        onNavigate("home")
                    },

                    autoTriggerSOS = true
                )

                // LEGAL
                "legal" -> LegalScreen {
                    onNavigate("home")
                }

                // GUIDE
                "guide" -> GuideScreen {
                    onNavigate("home")
                }

                // ALERTS
                "alerts" -> AlertsScreen {
                    onNavigate("home")
                }

                // FALLBACK
                else -> {
                    onNavigate("home")
                }
            }
        }
    }
}