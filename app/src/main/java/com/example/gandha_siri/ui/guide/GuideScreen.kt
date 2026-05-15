package com.example.gandha_siri.ui.guide

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import com.example.gandha_siri.R
import com.example.gandha_siri.ui.components.AppHeader

@Composable
fun GuideScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
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

        AppHeader(stringResource(R.string.farmer_guide), onBack)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            GuideCard(
                title = stringResource(R.string.legal_guidelines_title),
                content = stringResource(R.string.legal_guidelines_content),
                onLinkClick = {
                    openLink("https://aranya.gov.in")
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            GuideCard(
                title = stringResource(R.string.harvesting_procedure_title),
                content = stringResource(R.string.harvesting_procedure_content),
                onLinkClick = {
                    openLink("https://aranya.gov.in/pages/view/sandalwood")
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            GuideCard(
                title = stringResource(R.string.tree_security_title),
                content = stringResource(R.string.tree_security_content),
                onLinkClick = {
                    openLink("https://karunadu.karnataka.gov.in/forest")
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            GuideCard(
                title = stringResource(R.string.growth_maintenance_title),
                content = stringResource(R.string.growth_maintenance_content),
                onLinkClick = {
                    openLink("https://icar.org.in")
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            GuideCard(
                title = stringResource(R.string.guide_important_note_title),
                content = stringResource(R.string.guide_important_note_content),
                onLinkClick = {
                    openLink("https://aranya.gov.in")
                }
            )
        }
    }
}

@Composable
fun GuideCard(
    title: String,
    content: String,
    onLinkClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onLinkClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.open_official_source))
            }
        }
    }
}