package com.example.exercise_1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity {

    private EditText urlEditText;
    private Button connectButton;
    private TextView textView;
    private WebView webView;

    private String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        urlEditText = findViewById(R.id.edittext_url);
        connectButton = findViewById(R.id.button_connect);
        textView = findViewById(R.id.textview_output);
        webView = findViewById(R.id.webview_output);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConnect(urlEditText);
            }
        });
    }

    protected void onConnect(final EditText urlEditText) {
        urlString = urlEditText.getText().toString();

        // Source: https://developer.android.com/training/volley/simple.html
        // and https://developer.android.com/guide/webapps/webview

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int response_length = response.length();

                        if (response_length < 500) {
                            // Display the characters of the response string.
                            textView.setText(response.substring(0, response_length));
                        } else {
                            // Display the first 500 characters of the response string.
                            textView.setText(response.substring(0, 500));
                        }
                        webView.loadData(response, "text/html", "UTF-8");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Source: https://developer.android.com/guide/topics/ui/notifiers/toasts
                        Context context = getApplicationContext();
                        String errorString = error.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        // Source: https://stackoverflow.com/questions/24700582/handle-volley-error
                        if (error instanceof TimeoutError) {
                            Toast.makeText(context, "TimeoutError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(context, "AuthFailureError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context, "ServerError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(context, "NetworkError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(context, "ParseError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(context, "NoConnectionError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ClientError) {
                            Toast.makeText(context, "ClientError", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, errorString, Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        // Source: https://stackoverflow.com/questions/13644510/make-edittext-lose-focus-on-clicking-a-button
        // move focus from EditText to textView
        urlEditText.clearFocus();
        textView.requestFocus();

        // Source: https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
        // hide the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }
}
