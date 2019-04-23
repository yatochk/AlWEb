package com.hhade.aweb

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            val config = YandexMetricaConfig.newConfigBuilder(getString(R.string.appmetrica_key)).build()
            YandexMetrica.activate(applicationContext, config)
            YandexMetrica.enableActivityAutoTracking(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}