package com.example.gandha_siri.ui.legal

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.gandha_siri.R
import com.example.gandha_siri.ui.components.AppHeader

@Composable
fun LegalScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    fun openLink(url: String) {

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        context.startActivity(intent)
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
            title =
                stringResource(R.string.legal_guide),

            onBack = onBack
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            LegalCard(

                title =
                    stringResource(
                        R.string.cultivation_rules
                    ),

                content =
                    stringResource(
                        R.string.cultivation_rules_content
                    ),

                onLinkClick = {

                    openLink(
                        "https://aranya.gov.in"
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            LegalCard(

                title =
                    stringResource(
                        R.string.harvest_rules
                    ),

                content =
                    stringResource(
                        R.string.harvest_rules_content
                    ),

                onLinkClick = {

                    openLink(
                        "https://aranya.gov.in/pages/view/sandalwood"
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            LegalCard(

                title =
                    stringResource(
                        R.string.transport_rules
                    ),

                content =
                    stringResource(
                        R.string.transport_rules_content
                    ),

                onLinkClick = {

                    openLink(
                        "https://karunadu.karnataka.gov.in/forest"
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            LegalCard(

                title =
                    stringResource(
                        R.string.important_notice
                    ),

                content =
                    stringResource(
                        R.string.important_notice_content
                    ),

                onLinkClick = {

                    openLink(
                        "https://aranya.gov.in"
                    )
                }
            )
        }
    }
}

@Composable
fun LegalCard(
    title: String,
    content: String,
    onLinkClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,

                fontWeight = FontWeight.Bold,

                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = content,

                style =
                    MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onLinkClick,

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(
                        R.string.open_official_source
                    )
                )
            }
        }
    }
}