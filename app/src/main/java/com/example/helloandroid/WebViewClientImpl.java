package com.example.helloandroid;

/*for Viewing webpage in WebView and not in other browsers
        https://developer.android.com/guide/webapps/webview*/
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClientImpl extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String URL) {
        view.loadUrl(URL);
        return true;
    }
}
