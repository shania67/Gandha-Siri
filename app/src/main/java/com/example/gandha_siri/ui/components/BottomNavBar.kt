package com.example.gandha_siri.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import com.example.gandha_siri.R

@Composable
fun BottomNavBar(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {

    NavigationBar(

        containerColor =
            MaterialTheme.colorScheme.surface
    ) {

        // HOME
        NavigationBarItem(

            selected =
                currentScreen == "home",

            onClick = {
                onNavigate("home")
            },

            label = {

                Text(
                    stringResource(R.string.home)
                )
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.Home,

                    contentDescription =
                        stringResource(R.string.home)
                )
            },

            colors = NavigationBarItemDefaults.colors(

                indicatorColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        )

        // TREE LIST
        NavigationBarItem(

            selected =
                currentScreen == "treelist",

            onClick = {
                onNavigate("treelist")
            },

            label = {

                Text(
                    stringResource(R.string.trees)
                )
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.List,

                    contentDescription =
                        stringResource(R.string.trees)
                )
            },

            colors = NavigationBarItemDefaults.colors(

                indicatorColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        )

        // AI ADVISOR
        NavigationBarItem(

            selected =
                currentScreen == "advisor",

            onClick = {
                onNavigate("advisor")
            },

            label = {

                Text(
                    stringResource(R.string.advisor)
                )
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.SmartToy,

                    contentDescription =
                        stringResource(R.string.advisor)
                )
            },

            colors = NavigationBarItemDefaults.colors(

                indicatorColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        )

        // TOOLS
        NavigationBarItem(

            selected =
                currentScreen == "tools",

            onClick = {
                onNavigate("tools")
            },

            label = {

                Text(
                    stringResource(R.string.tools)
                )
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.Build,

                    contentDescription =
                        stringResource(R.string.tools)
                )
            },

            colors = NavigationBarItemDefaults.colors(

                indicatorColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}