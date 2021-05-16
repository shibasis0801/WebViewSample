package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat



class MainActivity : AppCompatActivity() {
    lateinit var webView: RevieveWebView

    fun onPermissionResult(permissions: Map<String, Boolean>) {
        permissions.forEach { Log.d(it.key, if(it.value) "Yes" else "No") }
        if (permissions.all { it.value }) {
//           webView.loadUrl("https://demov4.revieve.com/myntra")
            webView.loadUrl("https://shibasis-patnaik.web.app/react-webcam")
        }
    }

    val permissionRequester = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { onPermissionResult(it) }

    fun requestPermissions(vararg permissions: String) {
        val pendingPermissions = permissions.filter { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED }
        if (pendingPermissions.isEmpty()) {
            onPermissionResult(permissions.associateWith { true })
        }
        else {
            permissionRequester.launch(permissions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = RevieveWebView(this)
        setContentView(webView)
        requestPermissions(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }


}