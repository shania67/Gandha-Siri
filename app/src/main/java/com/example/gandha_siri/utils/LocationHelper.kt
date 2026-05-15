package com.example.gandha_siri.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

import androidx.core.content.ContextCompat

import com.example.gandha_siri.R
import com.google.android.gms.location.LocationServices

object LocationHelper {

    @SuppressLint("MissingPermission")
    fun getLastLocation(
        context: Context,
        onResult: (String) -> Unit
    ) {

        // CHECK PERMISSION
        val permissionGranted =

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {

            onResult(
                context.getString(
                    R.string.location_permission_denied
                )
            )

            return
        }

        val client =
            LocationServices
                .getFusedLocationProviderClient(context)

        client.lastLocation

            .addOnSuccessListener { location ->

                if (location != null) {

                    val text =

                        "${context.getString(R.string.lat)}: " +
                                "${location.latitude}, " +

                                "${context.getString(R.string.lng)}: " +
                                "${location.longitude}"

                    onResult(text)

                } else {

                    onResult(
                        context.getString(
                            R.string.location_unavailable
                        )
                    )
                }
            }

            .addOnFailureListener {

                onResult(
                    context.getString(
                        R.string.location_failed
                    )
                )
            }
    }
}