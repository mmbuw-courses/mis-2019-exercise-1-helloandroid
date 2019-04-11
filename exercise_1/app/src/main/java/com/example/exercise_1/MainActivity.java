package com.example.exercise_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

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

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConnect(urlEditText);
            }
        });
    }

    protected void onConnect(EditText urlEditText) {
        urlString = urlEditText.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
