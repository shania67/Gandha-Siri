package com.example.gandha_siri.ui.home

import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Shield

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {

    val context = LocalContext.current

    val prefs = context.getSharedPreferences(
        "gandha_siri_prefs",
        Context.MODE_PRIVATE
    )

    var name by remember {
        mutableStateOf("")
    }

    var farm by remember {
        mutableStateOf("")
    }

    // LOAD PROFILE
    LaunchedEffect(Unit) {

        name =
            prefs.getString(
                "user_name",
                context.getString(R.string.default_farmer)
            )
                ?: context.getString(R.string.default_farmer)

        farm =
            prefs.getString(
                "farm_name",
                context.getString(R.string.default_farm)
            )
                ?: context.getString(R.string.default_farm)
    }

    val db = AppDatabase.getDatabase(context)

    val trees by db.treeDao()
        .getAllTrees()
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    val alerts by db.treeDao()
        .getAllAlerts()
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF4EDE4),
                        Color(0xFFE6D3B3)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        stringResource(
                            R.string.greeting
                        )
                    )

                    Text(
                        text = name,

                        fontWeight = FontWeight.Bold,

                        style =
                            MaterialTheme.typography.titleMedium
                    )
                }

                Column(
                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(
                        "${stringResource(R.string.farm)}: $farm"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // STATS
            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                StatCard(
                    title =
                        stringResource(R.string.trees),

                    value = trees.size.toString()
                ) {

                    onNavigate("treelist")
                }

                StatCard(
                    title =
                        stringResource(R.string.alerts),

                    value = alerts.size.toString()
                ) {

                    onNavigate("alerts")
                }

                StatCard(
                    title =
                        stringResource(R.string.growth),

                    value =
                        stringResource(R.string.view)
                ) {

                    onNavigate("map")
                }
            }

            Spacer(
                modifier = Modifier.height(26.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string.quick_actions
                    ),

                fontWeight = FontWeight.Bold,

                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // ACTION GRID
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    ActionCard(
                        title =
                            stringResource(
                                R.string.add_tree
                            ),

                        icon = Icons.Default.Add
                    ) {

                        onNavigate("addtree")
                    }

                    ActionCard(
                        title =
                            stringResource(
                                R.string.view_trees
                            ),

                        icon = Icons.Default.List
                    ) {

                        onNavigate("treelist")
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    ActionCard(
                        title =
                            stringResource(
                                R.string.guide
                            ),

                        icon = Icons.Default.Book
                    ) {

                        onNavigate("guide")
                    }

                    ActionCard(
                        title =
                            stringResource(
                                R.string.advisor
                            ),

                        icon = Icons.Default.Man
                    ) {

                        onNavigate("advisor")
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    ActionCard(
                        title =
                            stringResource(
                                R.string.security
                            ),

                        icon = Icons.Default.Shield
                    ) {

                        onNavigate("security")
                    }
                }
            }
        }

        // SOS BUTTON
        FloatingActionButton(

            onClick = {

                onNavigate("security_sos")
            },

            containerColor = Color.Red,

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {

            Text(
                text = "SOS",

                color = Color.White
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,

        modifier = Modifier.width(100.dp),

        shape = RoundedCornerShape(14.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = value,

                fontWeight = FontWeight.Bold,

                style =
                    MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = title
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,

        modifier = Modifier
            .width(160.dp)
            .height(110.dp),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),

            verticalArrangement =
                Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = icon,

                contentDescription = title
            )

            Text(
                text = title,

                fontWeight = FontWeight.SemiBold
            )
        }
    }
}