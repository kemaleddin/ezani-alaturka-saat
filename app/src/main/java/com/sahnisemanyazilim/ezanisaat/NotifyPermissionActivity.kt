package com.sahnisemanyazilim.ezanisaat

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.kemalettinsargin.mylib.BaseFragmentActivity
import com.kemalettinsargin.mylib.Util

/**
 * Written by "كمال الدّين صارغين"  on 29.01.2024.
 * و من الله توفیق
 */
class NotifyPermissionActivity : BaseFragmentActivity() {
    private var btnBack: Button? = null
    private var btnOk:Button?=null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
                Util.showToast(this,getString(R.string.notify_permissions_info))
            }
            onBackPressedDispatcher.onBackPressed()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notify_permission_activity)
        createItems()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            loadItems()
        }else
            onBackPressedDispatcher.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun loadItems() {
        btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        btnOk?.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun createItems() {
        btnBack= findViewById(R.id.btn_cancel)
        btnOk=findViewById(R.id.btn_ok)

    }


}