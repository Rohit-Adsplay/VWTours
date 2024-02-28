package com.veenaworldwide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)
        val pbProgress: ProgressBar = findViewById(R.id.pbProgress)

        val url = intent.getStringExtra("urlValue").toString()

        fetchFcmToken()

        webView.getSettings().setLoadsImagesAutomatically(true)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setSupportMultipleWindows(true);

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                pbProgress.visibility = View.VISIBLE
                this@MainActivity.setProgress(progress * 100)
                if (progress == 100) pbProgress.visibility = View.GONE
            }
        })

        webView.setWebViewClient(WebViewClient())
        webView.loadUrl(url)
    }


    private fun fetchFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("DeviceID", "fetchFcmToken: " + task.exception)
                return@OnCompleteListener
            }
            val token = task.result.toString()
            Log.e("token", token)
        })

    }
}