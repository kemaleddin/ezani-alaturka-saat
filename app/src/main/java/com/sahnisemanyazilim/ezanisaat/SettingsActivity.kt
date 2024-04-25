package com.sahnisemanyazilim.ezanisaat

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import com.google.gson.reflect.TypeToken
import com.kemalettinsargin.mylib.Util
import com.sahnisemanyazilim.ezanisaat.enums.TimeEnum
import com.sahnisemanyazilim.ezanisaat.model.Town
import com.sahnisemanyazilim.ezanisaat.notifications.AlarmHelper
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
                Util.showToast(this,getString(R.string.notify_permissions_info))
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.ayarlar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS
            )!= PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS))
                permissionForNotifications()
            else
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    private fun permissionForNotifications() {
        startActivity(Intent(this, NotifyPermissionActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        var towns: List<Town>? = null
        var defaultTown: Town? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?,
        ) {
            if (sharedPreferences == null || key == null)
                return
            AlarmHelper.setAlarms(requireActivity(),sharedPreferences,key)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
            super.onPause()
        }

        private fun setData() {
            val gson = Util.getGson()
            towns = gson.fromJson<List<Town>>(
                Util.getPref(activity, C.KEY_LOCATIONS),
                object : TypeToken<List<Town?>?>() {}.type
            )
            defaultTown = towns?.firstOrNull { it.isActive }
        }
    }
}