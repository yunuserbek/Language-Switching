package com.example.languageswitching

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.util.Log
import java.lang.Exception
import java.util.*

class LocaleChanger(private val context: Context) {

    var prefences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var editor : SharedPreferences.Editor = prefences.edit()

    fun wrapContext(): Context {

        val savedLocale =
            createLocaleFromSavedLanguage()

        Locale.setDefault(savedLocale)

        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)

        return context.createConfigurationContext(newConfig)
    }

    private fun createLocaleFromSavedLanguage(): Locale {

        return Locale(prefences.getString("Language","en"))
    }

    fun overrideLocale(locale: Locale) {

        try {
            editor.putString("Language",locale.language)
            editor.apply()

            val savedLocale = createLocaleFromSavedLanguage()

            Locale.setDefault(savedLocale)

            val newConfig = Configuration()
            newConfig.setLocale(savedLocale)

            //context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)

            if (context != context.applicationContext) {
                context.applicationContext.resources.run {
                    updateConfiguration(
                        newConfig,
                        displayMetrics
                    )
                }
            }

        }catch (e:Exception){
            Log.i("Exception",e.toString())
        }

    }
}