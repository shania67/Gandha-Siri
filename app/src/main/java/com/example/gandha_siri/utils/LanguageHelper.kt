package com.example.gandha_siri.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageHelper {

    fun setLocale(context: Context, languageCode: String) {

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

    fun applyLanguage(activity: Activity, langCode: String) {
        setLocale(activity, langCode)
        activity.recreate() // 🔥 refresh UI
    }
}