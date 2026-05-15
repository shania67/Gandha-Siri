package com.example.gandha_siri.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import coil.compose.rememberAsyncImagePainter

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.ui.components.AppHeader

import kotlinx.coroutines.launch

@Composable
fun TreeDetailsScreen(
    tree: Tree,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val scope = rememberCoroutineScope()

    var showConfirm by remember {
        mutableStateOf(false)
    }

    // MATURITY CALCULATION
    val girthValue =
        tree.girth.toIntOrNull() ?: 0

    val ageValue =
        tree.age.toIntOrNull() ?: 0

    val maturityStatus = when {

        girthValue >= 50 || ageValue >= 15 ->
            context.getString(R.string.mature_tree)

        girthValue in 30..49 ->
            context.getString(R.string.growing_tree)

        girthValue in 10..29 ->
            context.getString(R.string.developing_tree)

        else ->
            context.getString(R.string.early_tree)
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
            stringResource(R.string.tree_details),
            onBack
        )

        // SCROLLABLE CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            // IMAGE
            if (tree.imageUri.isNotEmpty()) {

                Image(
                    painter = rememberAsyncImagePainter(
                        tree.imageUri
                    ),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),

                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TREE DETAILS CARD
            Card(
                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        "${stringResource(R.string.tree_id)}: ${tree.id}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "${stringResource(R.string.tree_name)}: ${tree.name}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "${stringResource(R.string.age)}: ${tree.age}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "${stringResource(R.string.girth)}: ${tree.girth}"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        stringResource(R.string.saved_location)
                    )

                    Text(tree.location)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "${stringResource(R.string.latitude)}: ${tree.latitude}"
                    )

                    Text(
                        "${stringResource(R.string.longitude)}: ${tree.longitude}"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MATURITY CARD
            Card(
                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEFEBE9)
                ),

                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.maturity_status),

                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = maturityStatus,

                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(
                            R.string.based_on_age_girth
                        ),

                        style = MaterialTheme.typography.bodySmall,

                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // DELETE BUTTON
            Button(
                onClick = {
                    showConfirm = true
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(R.string.delete_tree),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    // DELETE CONFIRMATION
    if (showConfirm) {

        AlertDialog(

            onDismissRequest = {
                showConfirm = false
            },

            title = {
                Text(
                    stringResource(R.string.delete_tree)
                )
            },

            text = {
                Text(
                    stringResource(
                        R.string.delete_confirm
                    )
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        scope.launch {

                            db.treeDao()
                                .deleteTree(tree)

                            onBack()
                        }
                    }
                ) {

                    Text(
                        stringResource(R.string.yes),
                        color = Color.Red
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showConfirm = false
                    }
                ) {

                    Text(
                        stringResource(R.string.cancel)
                    )
                }
            }
        )
    }
}