package com.piciu.bricklist

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs: SharedPreferences = getSharedPreferences(Globals.PREFS_FILENAME, 0)

        url.setText(prefs.getString(Globals.ADDRESS_URL, "http://fcds.cs.put.poznan.pl/MyWeb/BL/"))

        applyChanges.setOnClickListener {
            val editor = prefs.edit()
            editor.putString(Globals.ADDRESS_URL, url.text.toString())
            editor.apply()
            finish()
        }
    }
}
