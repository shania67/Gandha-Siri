package com.example.gandha_siri.ui.tools

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import com.example.gandha_siri.R
import com.example.gandha_siri.ui.components.AppHeader

import java.util.Locale

@Composable
fun ProfileScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val activity = context as Activity

    val prefs = context.getSharedPreferences(
        "gandha_siri_prefs",
        Context.MODE_PRIVATE
    )

    var name by remember {
        mutableStateOf(prefs.getString("user_name", "") ?: "")
    }

    var farm by remember {
        mutableStateOf(prefs.getString("farm_name", "") ?: "")
    }

    var age by remember {
        mutableStateOf(prefs.getString("user_age", "") ?: "")
    }

    var mobileNumber by remember {
        mutableStateOf(prefs.getString("user_mobile", "") ?: "")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFF4EDE4), Color(0xFFE6D3B3))
                )
            )
    ) {

        AppHeader(stringResource(R.string.profile_setup), onBack)

        Column(modifier = Modifier.padding(16.dp)) {

            // FARMER NAME
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(stringResource(R.string.farmer_name))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // FARM NAME
            OutlinedTextField(
                value = farm,
                onValueChange = { farm = it },
                label = {
                    Text(stringResource(R.string.farm_name))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // AGE
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = {
                    Text(stringResource(R.string.age))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // MOBILE NUMBER
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = {
                    Text(stringResource(R.string.mobile_number))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // SAVE PROFILE
            Button(
                onClick = {

                    prefs.edit()
                        .putString("user_name", name)
                        .putString("farm_name", farm)
                        .putString("user_age", age)
                        .putString("user_mobile", mobileNumber)
                        .apply()

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_profile))
            }

            Spacer(modifier = Modifier.height(30.dp))

            // LANGUAGE SECTION
            Text(
                text = stringResource(R.string.select_language),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // ENGLISH BUTTON
                Button(
                    onClick = {

                        setLocale(context, "en")

                        prefs.edit()
                            .putString("app_language", "en")
                            .apply()

                        activity.recreate()
                    }
                ) {
                    Text("English")
                }

                // KANNADA BUTTON
                Button(
                    onClick = {

                        setLocale(context, "kn")

                        prefs.edit()
                            .putString("app_language", "kn")
                            .apply()

                        activity.recreate()
                    }
                ) {
                    Text("ಕನ್ನಡ")
                }
            }
        }
    }
}

// LANGUAGE FUNCTION
fun setLocale(context: Context, language: String) {

    val locale = Locale(language)

    Locale.setDefault(locale)

    val config = context.resources.configuration

    config.setLocale(locale)

    context.resources.updateConfiguration(
        config,
        context.resources.displayMetrics
    )
}
