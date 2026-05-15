package com.example.gandha_siri.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

import com.example.gandha_siri.MainActivity
import com.example.gandha_siri.R

object NotificationHelper {

    private const val CHANNEL_ID = "sos_alert_channel"
    private const val CHANNEL_NAME = "SOS Alerts"

    fun showSOSNotification(
        context: Context,
        message: String
    ) {

        // ✅ Notification sound
        val soundUri = RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_NOTIFICATION
        )

        // ✅ Open app when notification clicked
        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        // ✅ Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {

                description = "Emergency SOS alerts"

                enableVibration(true)

                vibrationPattern = longArrayOf(
                    0,
                    1000,
                    500,
                    1000
                )

                setSound(soundUri, attributes)
            }

            val manager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            // ✅ Delete old channel to refresh settings
            manager.deleteNotificationChannel(CHANNEL_ID)

            manager.createNotificationChannel(channel)
        }

        // ✅ Android 13+ permission check
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            val granted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                return
            }
        }

        // ✅ Build notification
        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("🚨 SOS ALERT")
                .setContentText(message)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setCategory(
                    NotificationCompat.CATEGORY_ALARM
                )
                .setVisibility(
                    NotificationCompat.VISIBILITY_PUBLIC
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(soundUri)
                .setVibrate(
                    longArrayOf(
                        0,
                        1000,
                        500,
                        1000
                    )
                )
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }
}