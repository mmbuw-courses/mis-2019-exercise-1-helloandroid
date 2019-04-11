package com.example.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // declare variables
    EditText urlField;
    Button connectBtn;
    TextView textView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        urlField = findViewById(R.id.urlField);
        connectBtn = findViewById(R.id.connectbtn);
        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                toastNoti("Error content:" + Integer.toString(errorCode) + description);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });

    }

    // event function
    public  void clickConnectBtn(View view) {
        // get text from text field
        String url= urlField.getText().toString();

        // TODO: send request and get response as result
        // Initiate new request queue
        RequestQueue queue = Volley.newRequestQueue(this);


        //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            // print result on text view
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastNoti(error.toString());
            }
        });
        //Add request to Request Queue
        queue.add(stringRequest);

        //create a blank page
        webView.loadUrl(url);
    }
    // Toast
    private void  toastNoti(String content) {
        Toast toast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }


    // Display website on webview
    private void webViewDisplay() {

    }
}

