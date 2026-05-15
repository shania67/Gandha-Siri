package com.example.gandha_siri.ui.addtree

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

import androidx.core.content.ContextCompat

import coil.compose.rememberAsyncImagePainter

import com.example.gandha_siri.R
import com.example.gandha_siri.data.db.AppDatabase
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.ui.components.AppHeader

import com.google.android.gms.location.LocationServices

import kotlinx.coroutines.launch

import kotlin.random.Random

@Composable
fun AddTreeScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val db = AppDatabase.getDatabase(context)

    val scope = rememberCoroutineScope()

    val fusedLocationClient =
        LocationServices
            .getFusedLocationProviderClient(context)

    // FORM STATES
    var treeName by remember {
        mutableStateOf("")
    }

    var girth by remember {
        mutableStateOf("")
    }

    var age by remember {
        mutableStateOf("")
    }

    // IMAGE
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // LOCATION
    var latitude by remember {
        mutableStateOf<Double?>(null)
    }

    var longitude by remember {
        mutableStateOf<Double?>(null)
    }

    var locationText by remember {

        mutableStateOf(
            context.getString(
                R.string.location_not_fetched
            )
        )
    }

    // ERROR
    var showError by remember {
        mutableStateOf(false)
    }

    // FETCH LOCATION
    fun fetchLocation() {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation

            .addOnSuccessListener { location ->

                if (location != null) {

                    latitude = location.latitude
                    longitude = location.longitude

                    locationText =
                        "${context.getString(R.string.lat)}: ${location.latitude}, " +
                                "${context.getString(R.string.lng)}: ${location.longitude}"

                } else {

                    locationText =
                        context.getString(
                            R.string.location_unavailable
                        )
                }
            }

            .addOnFailureListener {

                locationText =
                    context.getString(
                        R.string.location_failed
                    )
            }
    }

    // IMAGE PICKER
    val imageLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            uri?.let {

                try {

                    context.contentResolver
                        .takePersistableUriPermission(
                            it,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )

                } catch (e: Exception) {

                    e.printStackTrace()
                }

                imageUri = it
            }
        }

    // LOCATION PERMISSION
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                fetchLocation()

            } else {

                locationText =
                    context.getString(
                        R.string.location_permission_denied
                    )
            }
        }

    // MAIN UI
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
            title =
                stringResource(R.string.add_tree),

            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
        ) {

            // IMAGE BUTTON
            Button(
                onClick = {

                    imageLauncher.launch(
                        arrayOf("image/*")
                    )
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(
                        R.string.select_image
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // IMAGE PREVIEW
            imageUri?.let {

                Image(
                    painter =
                        rememberAsyncImagePainter(it),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),

                    contentScale = ContentScale.Crop
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // LOCATION BUTTON
            Button(
                onClick = {

                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(
                        R.string.fetch_location
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = locationText
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // TREE NAME
            OutlinedTextField(
                value = treeName,

                onValueChange = {
                    treeName = it
                },

                label = {
                    Text(
                        stringResource(
                            R.string.tree_name
                        )
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // GIRTH
            OutlinedTextField(
                value = girth,

                onValueChange = {
                    girth = it
                },

                label = {
                    Text(
                        stringResource(
                            R.string.girth
                        )
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number,

                    imeAction = ImeAction.Next
                )
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // AGE
            OutlinedTextField(
                value = age,

                onValueChange = {
                    age = it
                },

                label = {
                    Text(
                        stringResource(
                            R.string.age
                        )
                    )
                },

                modifier = Modifier.fillMaxWidth(),

                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number,

                    imeAction = ImeAction.Done
                )
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // ERROR MESSAGE
            if (showError) {

                Text(
                    text =
                        stringResource(
                            R.string.fill_all_fields
                        ),

                    color = Color.Red
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            // SAVE BUTTON
            Button(
                onClick = {

                    if (
                        treeName.isBlank() ||
                        girth.isBlank() ||
                        age.isBlank() ||
                        imageUri == null ||
                        latitude == null ||
                        longitude == null
                    ) {

                        showError = true
                        return@Button
                    }

                    showError = false

                    val tree = Tree(

                        id =
                            "GS-${Random.nextInt(1000, 9999)}",

                        name = treeName,

                        girth = girth,

                        age = age,

                        location = locationText,

                        latitude = latitude!!,

                        longitude = longitude!!,

                        imageUri = imageUri.toString()
                    )

                    scope.launch {

                        db.treeDao()
                            .insertTree(tree)

                        onBack()
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    stringResource(
                        R.string.save_tree
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}