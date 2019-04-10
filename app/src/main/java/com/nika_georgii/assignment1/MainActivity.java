package com.nika_georgii.assignment1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button connectButton;
    private EditText input;
    private WebView webView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView);
        input = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConnectButtonClick(input.getText().toString());
            }
        });

        resetViews();
        setVisibilityOfProgressBar(false);
    }

    private void resetViews() {
        webView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    private void setVisibilityOfProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void onConnectButtonClick(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        resetViews();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setVisibilityOfProgressBar(false);
                if (response.substring(0, 100).toLowerCase().startsWith("<!doctype html>")) {
                    webView.setVisibility(View.VISIBLE);
                    webView.loadDataWithBaseURL("", response, "text/html", "UTF-8", "");
                } else {
                    textView.setText(response.substring(0, 500));
                    textView.setVisibility(View.VISIBLE);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setVisibilityOfProgressBar(false);
                Toast.makeText(getApplicationContext(), getPrettyErrorText(error), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        setVisibilityOfProgressBar(true);
    }

    private String getPrettyErrorText(VolleyError error) {
        return "Oooops, something went wrong. Error: " + error.getMessage();
    }
}
