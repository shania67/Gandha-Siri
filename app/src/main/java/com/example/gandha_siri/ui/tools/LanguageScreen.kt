package com.example.gandha_siri.ui.tools

import android.app.Activity
import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.example.gandha_siri.R
import com.example.gandha_siri.ui.components.AppHeader
import com.example.gandha_siri.utils.LanguageHelper

@Composable
fun LanguageScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val activity = context as Activity

    val prefs = context.getSharedPreferences(
        "gandha_siri_prefs",
        Context.MODE_PRIVATE
    )

    // ONLY ENGLISH + KANNADA
    val languages = listOf(
        Pair(stringResource(R.string.english), "en"),
        Pair(stringResource(R.string.kannada), "kn")
    )

    var selected by remember {
        mutableStateOf(
            prefs.getString("app_language", "en") ?: "en"
        )
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

        AppHeader(
            title = stringResource(R.string.language),
            onBack = onBack
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            languages.forEach { (name, code) ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selected == code,
                            onClick = {
                                selected = code
                                prefs.edit()
                                    .putString("app_language", code)
                                    .apply()

                                LanguageHelper.applyLanguage(activity, code)
                            }
                        )
                        .padding(vertical = 12.dp)
                ) {

                    RadioButton(
                        selected = selected == code,
                        onClick = null
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
