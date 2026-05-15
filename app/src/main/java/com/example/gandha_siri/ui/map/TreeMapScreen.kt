package com.example.gandha_siri.ui.map

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.ui.components.AppHeader

import java.text.NumberFormat
import java.util.Locale

@Composable
fun TreeMapScreen(
    onBack: () -> Unit,
    onTreeClick: (Tree) -> Unit
) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val trees by db.treeDao()
        .getAllTrees()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    var selectedTree by remember { mutableStateOf<Tree?>(null) }
    var filter by remember { mutableStateOf("All") }

    // 🔹 FILTER LOGIC
    val filteredTrees = trees.filter { tree ->
        val girth = tree.girth.toIntOrNull() ?: 0
        val age = tree.age.toIntOrNull() ?: 0

        when (filter) {
            "Mature" -> girth >= 50 || age >= 15
            "Growing" -> girth in 10..49
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFF4EDE4), Color(0xFFE6D3B3))
                )
            )
            .verticalScroll(rememberScrollState())
    ) {

        AppHeader(stringResource(R.string.tree_map), onBack)

        // 🔹 FILTER CHIPS
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Growing", "Mature").forEach { type ->
                FilterChip(
                    selected = filter == type,
                    onClick = { filter = type },
                    label = { Text(type) }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .padding(16.dp)
                .background(
                    Color(0xFFF7F2EC),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {

            // 🔹 TREE MARKERS
            filteredTrees.forEachIndexed { index, tree ->

                val girth = tree.girth.toIntOrNull() ?: 0
                val age = tree.age.toIntOrNull() ?: 0

                val color = when {
                    girth >= 50 || age >= 15 -> Color(0xFF4E342E)
                    girth in 30..49 -> Color(0xFFFF9800)
                    girth in 10..29 -> Color(0xFF4CAF50)
                    else -> Color(0xFF81C784)
                }

                val isSelected = tree == selectedTree

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.3f else 1f,
                    animationSpec = spring()
                )

                val x = (index % 3) * 120
                val y = (index / 3) * 140

                Column(
                    modifier = Modifier
                        .offset(x.dp, y.dp)
                        .clickable { selectedTree = tree },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // 🔥 ANIMATED MARKER
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .scale(scale)
                            .background(color, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Park,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("TR-${tree.id}", fontSize = 12.sp)
                }
            }
        }

        // 🔥 GROWTH ANALYSIS SECTION
        AnimatedVisibility(
            visible = selectedTree != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {

            selectedTree?.let { tree ->

                val ageVal = tree.age.toIntOrNull() ?: 0
                val girthVal = tree.girth.toIntOrNull() ?: 0

                // Simulated calculations
                val targetAge = 25
                val yearsToHarvest = if (ageVal < targetAge) targetAge - ageVal else 0
                val marketValue = girthVal * ageVal * 450
                val currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
                val marketValueFormatted = currencyFormat.format(marketValue).replace(".00", "")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.maturity_index),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4E342E)
                    )

                    Text(
                        text = stringResource(R.string.asset_value_estimation),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔹 THE ASSET CARD
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF4E342E)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {

                            Text(
                                text = stringResource(R.string.time_to_harvest),
                                color = Color.White.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.labelMedium
                            )

                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = yearsToHarvest.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.yrs),
                                    color = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                thickness = 0.5.dp,
                                color = Color.White.copy(alpha = 0.2f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.wood_grade),
                                        color = Color.White.copy(alpha = 0.7f),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Text(
                                        text = stringResource(R.string.premium_heartwood),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = stringResource(R.string.market_value),
                                        color = Color.White.copy(alpha = 0.7f),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Text(
                                        text = marketValueFormatted,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔹 GROWTH BARS
                    GrowthBar(
                        label = stringResource(R.string.girth_intensity),
                        valueText = "${girthVal}CM",
                        progress = (girthVal / 100f).coerceIn(0f, 1f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    GrowthBar(
                        label = stringResource(R.string.biological_age),
                        valueText = "${ageVal}YRS",
                        progress = (ageVal / 30f).coerceIn(0f, 1f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.simulated_data_disclaimer),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onTreeClick(tree) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4E342E)
                        )
                    ) {
                        Text(stringResource(R.string.view_details))
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun GrowthBar(label: String, valueText: String, progress: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
            Text(
                text = valueText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color(0xFFEFEBE9), CircleShape)
        ) {
            val width = maxWidth
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0.01f, 1f))
                    .fillMaxHeight()
                    .background(Color(0xFF4E342E), CircleShape)
            )
            // Circular Thumb
            Box(
                modifier = Modifier
                    .offset(x = (width * progress.coerceIn(0f, 1f)) - 6.dp)
                    .size(12.dp)
                    .background(Color(0xFF4E342E), CircleShape)
                    .align(Alignment.CenterStart)
            )
        }
    }
}
