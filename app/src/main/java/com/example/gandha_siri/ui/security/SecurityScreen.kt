package com.example.gandha_siri.ui.security

import android.Manifest
import android.content.Context
import android.os.Build

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Alert
import com.example.gandha_siri.ui.components.AppHeader
import com.example.gandha_siri.utils.LocationHelper
import com.example.gandha_siri.utils.NotificationHelper

@Composable
fun SecurityScreen(
    onBack: () -> Unit,
    autoTriggerSOS: Boolean = false
) {

    val context = LocalContext.current

    val db = AppDatabase.getDatabase(context)

    val scope = rememberCoroutineScope()

    val prefs = context.getSharedPreferences(
        "security_prefs",
        Context.MODE_PRIVATE
    )

    // NOTIFICATION PERMISSION
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { }

    // LOAD SAVED STATES
    var fencing by remember {
        mutableStateOf(
            prefs.getBoolean("fencing", true)
        )
    }

    var surveillance by remember {
        mutableStateOf(
            prefs.getBoolean("surveillance", false)
        )
    }

    var gps by remember {
        mutableStateOf(
            prefs.getBoolean("gps", true)
        )
    }

    var ranger by remember {
        mutableStateOf(
            prefs.getBoolean("ranger", false)
        )
    }

    var isSending by remember {
        mutableStateOf(false)
    }

    var lastLocation by remember {
        mutableStateOf(
            context.getString(R.string.location_not_fetched)
        )
    }

    val systems =
        listOf(fencing, surveillance, gps, ranger)

    val activeCount =
        systems.count { it }

    val securityScore =
        (activeCount * 100) / systems.size

    // SAVE FUNCTION
    fun saveState() {

        prefs.edit()
            .putBoolean("fencing", fencing)
            .putBoolean("surveillance", surveillance)
            .putBoolean("gps", gps)
            .putBoolean("ranger", ranger)
            .apply()
    }

    // SOS FUNCTION
    fun triggerSOS() {

        // REQUEST NOTIFICATION PERMISSION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }

        isSending = true

        LocationHelper.getLastLocation(context) { loc ->

            lastLocation = loc

            scope.launch {

                delay(1200)

                val message =
                    "🚨 Emergency Alert!\nLocation: $loc"

                db.treeDao().insertAlert(
                    Alert(
                        message = message,
                        timestamp = System.currentTimeMillis()
                    )
                )

                NotificationHelper.showSOSNotification(
                    context,
                    message
                )

                isSending = false
            }
        }
    }

    // AUTO SOS
    LaunchedEffect(autoTriggerSOS) {

        if (autoTriggerSOS) {
            triggerSOS()
        }
    }

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

        AppHeader(
            title = stringResource(R.string.defense_system),
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            // SOS CARD
            Card(
                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = {
                            triggerSOS()
                        },

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),

                        modifier = Modifier.size(100.dp),

                        shape = RoundedCornerShape(50)
                    ) {

                        if (isSending) {

                            CircularProgressIndicator(
                                color = Color.White
                            )

                        } else {

                            Text(
                                stringResource(R.string.sos),
                                color = Color.White
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        stringResource(
                            R.string.emergency_signal
                        ),

                        fontWeight = FontWeight.Bold
                    )

                    Text(

                        text =
                            if (isSending)
                                stringResource(
                                    R.string.sending_alert
                                )
                            else
                                stringResource(
                                    R.string.tap_sos
                                ),

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "${stringResource(R.string.last_location)}: $lastLocation",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color = Color.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // SECURITY SCORE
            val progress by animateFloatAsState(
                targetValue =
                    securityScore / 100f,

                label = "security_progress"
            )

            Text(
                text =
                    "${stringResource(R.string.security_score)}: $securityScore%",

                fontWeight = FontWeight.Bold
            )

            LinearProgressIndicator(
                progress = { progress },

                modifier =
                    Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                stringResource(
                    R.string.infrastructure_status
                ),

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            SecurityToggle(
                title =
                    stringResource(
                        R.string.electric_fencing
                    ),

                isActive = fencing
            ) {

                fencing = it
                saveState()
            }

            SecurityToggle(
                title =
                    stringResource(
                        R.string.night_surveillance
                    ),

                isActive = surveillance
            ) {

                surveillance = it
                saveState()
            }

            SecurityToggle(
                title =
                    stringResource(
                        R.string.gps_tracking
                    ),

                isActive = gps
            ) {

                gps = it
                saveState()
            }

            SecurityToggle(
                title =
                    stringResource(
                        R.string.ranger_station
                    ),

                isActive = ranger
            ) {

                ranger = it
                saveState()
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}

@Composable
fun SecurityToggle(
    title: String,
    isActive: Boolean,
    onToggle: (Boolean) -> Unit
) {

    Card(
        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                if (isActive)
                    Color(0xFFE8F5E9)
                else
                    Color.White
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                title,
                fontWeight = FontWeight.Medium
            )

            Switch(
                checked = isActive,

                onCheckedChange = onToggle
            )
        }
    }
}