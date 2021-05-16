package com.example.myapplication

import android.content.Context
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings




fun logDebug(message: String?) {
    Log.d("OVERLORD", message ?: "NULL")
}

// avc: denied { read } for name="u:object_r:vendor_persist_camera_prop:s0" dev="tmpfs" ino=1253 scontext=u:r:untrusted_app:s0:c77,c257,c512,c768 tcontext=u:object_r:vendor_persist_camera_prop:s0 tclass=file permissive=0 app=com.example.myapplication
// E/libc: Access denied finding property "vendor.camera.aux.packagelist"
// E/libc: Access denied finding property "persist.vendor.camera.privapp.list"
// E/cr_VideoCapture: CameraDevice.StateCallback onOpened
// W/VideoCapabilities: Unsupported mime image/vnd.android.heic
// W/VideoCapabilities: Unrecognized profile/level 0/3 for video/mpeg2
// I/CameraManagerGlobal: Connecting to camera service
// W/cr_media: Requires MODIFY_AUDIO_SETTINGS and RECORD_AUDIO. No audio device will be available for recording
// Uses https://github.com/mozmorris/react-webcam/blob/master/src/react-webcam.tsx
class RevieveWebView(context: Context): WebView(context) {
    init {
        setWebContentsDebuggingEnabled(true)
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true

        }

        webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.resources?.forEach {
                    logDebug("permission: $it")
                }
                request!!.grant(arrayOf(
                    PermissionRequest.RESOURCE_VIDEO_CAPTURE,
                    PermissionRequest.RESOURCE_AUDIO_CAPTURE
                ))
                logDebug(request?.resources?.toString())
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                logDebug("Cancelled")
            }

        }
    }


}