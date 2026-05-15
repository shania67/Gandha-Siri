package com.example.gandha_siri.ui.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Alert
import com.example.gandha_siri.ui.components.AppHeader

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertsScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val scope = rememberCoroutineScope()

    val alerts by db.treeDao()
        .getAllAlerts()
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    Column(
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

        // HEADER
        AppHeader(
            stringResource(R.string.alerts),
            onBack
        )

        // CLEAR BUTTON
        if (alerts.isNotEmpty()) {

            Button(
                onClick = {

                    scope.launch {

                        db.treeDao()
                            .clearAlerts()
                    }
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),

                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    )
                    .fillMaxWidth()
            ) {

                Text(
                    text = stringResource(
                        R.string.clear_alerts
                    ),

                    color = Color.White
                )
            }
        }

        // ALERT LIST
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // EMPTY STATE
            if (alerts.isEmpty()) {

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = stringResource(
                                R.string.no_alerts
                            ),

                            style =
                                MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.farm_safe
                            ),

                            style =
                                MaterialTheme.typography.bodySmall,

                            color = Color.Gray
                        )
                    }
                }
            }

            // ALERT ITEMS
            items(alerts) { alert ->

                AlertCard(alert)
            }
        }
    }
}

@Composable
fun AlertCard(alert: Alert) {

    val date = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    ).format(
        Date(alert.timestamp)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        shape = MaterialTheme.shapes.medium
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = alert.message,

                style =
                    MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = date,

                style =
                    MaterialTheme.typography.bodySmall,

                color = Color.Gray
            )
        }
    }
}