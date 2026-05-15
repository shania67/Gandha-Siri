package com.example.gandha_siri.ui.advisor

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun AdvisorScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    var userInput by remember {
        mutableStateOf("")
    }

    var response by remember {
        mutableStateOf("")
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

        // HEADER
        AppHeader(
            stringResource(R.string.ai_advisor),
            onBack
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // INPUT FIELD
            OutlinedTextField(
                value = userInput,

                onValueChange = {
                    userInput = it
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text(
                        stringResource(R.string.ask_question)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ASK BUTTON
            Button(
                onClick = {

                    response = generateResponse(
                        context,
                        userInput
                    )
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(R.string.ask)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // RESPONSE CARD
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text =
                        if (response.isEmpty())
                            stringResource(R.string.advisor_hint)
                        else
                            response,

                    modifier = Modifier.padding(16.dp),

                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// AI RESPONSE LOGIC
fun generateResponse(
    context: Context,
    input: String
): String {

    val text = input.lowercase()

    return when {

        // WATER
        text.contains("water") ||
                text.contains("watering") ||
                text.contains("ನೀರು") ->

            context.getString(
                R.string.response_water
            )

        // SUNLIGHT
        text.contains("sun") ||
                text.contains("sunlight") ||
                text.contains("ಬೆಳಕು") ->

            context.getString(
                R.string.response_sunlight
            )

        // SOIL
        text.contains("soil") ||
                text.contains("ಮಣ್ಣು") ->

            context.getString(
                R.string.response_soil
            )

        // GROWTH
        text.contains("growth") ||
                text.contains("grow") ||
                text.contains("ಬೆಳವಣಿಗೆ") ->

            context.getString(
                R.string.response_growth
            )

        // PEST
        text.contains("pest") ||
                text.contains("insect") ||
                text.contains("ಕೀಟ") ->

            context.getString(
                R.string.response_pest
            )

        // FERTILIZER
        text.contains("fertilizer") ||
                text.contains("compost") ||
                text.contains("ಗೊಬ್ಬರ") ->

            context.getString(
                R.string.response_fertilizer
            )

        // EMPTY
        text.isBlank() ->

            context.getString(
                R.string.response_empty
            )

        // DEFAULT
        else ->

            context.getString(
                R.string.response_default
            )
    }
}