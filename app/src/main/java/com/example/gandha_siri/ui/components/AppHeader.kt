package com.example.gandha_siri.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import com.example.gandha_siri.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    title: String,
    onBack: () -> Unit,
    showProfile: Boolean = false,
    onProfileClick: () -> Unit = {}
) {

    TopAppBar(

        title = {

            Text(
                text = title,

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold
            )
        },

        navigationIcon = {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,

                    contentDescription =
                        stringResource(R.string.back)
                )
            }
        },

        actions = {

            if (showProfile) {

                IconButton(
                    onClick = onProfileClick
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.AccountCircle,

                        contentDescription =
                            stringResource(R.string.profile)
                    )
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(

            containerColor =
                MaterialTheme.colorScheme.surface
        )
    )
}