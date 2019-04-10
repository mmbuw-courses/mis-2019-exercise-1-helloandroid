package com.example.hello_android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import android.text.method.ScrollingMovementMethod;

// for Volley I used this "https://developer.android.com/training/volley/requestqueue"
// for general questions "https://developer.android.com/training/basics/firstapp" was used.

public class MainActivity extends AppCompatActivity {

    EditText uInput;
    TextView uOutput;
    RequestQueue requestQueue;
    Toast toast;
    WebView uWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // on click of button "connect"
    public void sendUrlText(View view){
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        uInput = (EditText) findViewById(R.id.editText);
        final String url = uInput.getText().toString();

        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display the html source code in the field under the input
                        uOutput = findViewById(R.id.textView);
                        uOutput.setText(response);
                        uOutput.setMovementMethod(new ScrollingMovementMethod());

                        // display the website under the source code properly
                        uWebsite = (WebView) findViewById(R.id.webView);
                        // open website in App
                        uWebsite.setWebViewClient(new WebViewClient());
                        uWebsite.loadUrl(url);
                        // it seems WebView has problems with the emulator; however,
                        // it worked totally fine when I tried it with a phone.
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Context context = getApplicationContext();
                        CharSequence text = error.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        //toast.setGravity(Gravity.TOP, 0,0);
                        toast.makeText(context, text, duration).show();
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}
