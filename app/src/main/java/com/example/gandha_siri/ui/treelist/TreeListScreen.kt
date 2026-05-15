package com.example.gandha_siri.ui.treelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.ui.components.AppHeader

@Composable
fun TreeListScreen(
    onBack: () -> Unit,
    onTreeClick: (Tree) -> Unit
) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val trees by db.treeDao().getAllTrees().collectAsState(initial = emptyList())

    Column {

        AppHeader(
            title = stringResource(R.string.tree_register),
            onBack = onBack
        )

        if (trees.isEmpty()) {
            Text(
                text = stringResource(R.string.no_trees_added),
                modifier = Modifier.padding(all=16.dp)
            )
        } else {
            LazyColumn {
                items(trees) { tree ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all=12.dp)
                            .clickable { onTreeClick(tree) }
                    ) {
                        Column(modifier = Modifier.padding(all=16.dp)) {
                            Text(
                                text = tree.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${stringResource(R.string.age)}: ${tree.age} | ${stringResource(R.string.girth)}: ${tree.girth}"
                            )
                        }
                    }
                }
            }
        }
    }
}