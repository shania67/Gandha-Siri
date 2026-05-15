package com.example.gandha_siri.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.example.gandha_siri.R
import com.example.gandha_siri.ui.components.AppHeader

@Composable
fun ToolsScreen(onNavigate: (String) -> Unit, onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFF4EDE4), Color(0xFFE6D3B3))
                )
            )
    ) {

        AppHeader(stringResource(R.string.tools), onBack)

        Column(modifier = Modifier.padding(16.dp)) {

            ToolCard("👤 " + stringResource(R.string.profile_setup)) {
                onNavigate("profile")
            }

            ToolCard("🌐 " + stringResource(R.string.language)) {
                onNavigate("language")
            }

            ToolCard("📞 " + stringResource(R.string.help)) {
                onNavigate("help")
            }
        }
    }
}

@Composable
fun ToolCard(title: String, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(20.dp)
        )
    }
}