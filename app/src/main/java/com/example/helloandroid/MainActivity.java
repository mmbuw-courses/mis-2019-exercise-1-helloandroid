package com.example.helloandroid;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.outText);
        final TextInputLayout textInput = (TextInputLayout) findViewById(R.id.URLInput);
        final Button connectBtn = (Button)  findViewById(R.id.bringTextBtn);

        Log.d("MainActivity", "Running Methods");
        changeText();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void changeText(){

        final TextInputLayout textInput = (TextInputLayout) findViewById(R.id.URLInput);
        final Button connectBtn = (Button)  findViewById(R.id.bringTextBtn);

        String textUrl= textInput.getEditText().getText().toString();
        connectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                httpText(textInput.getEditText().getText().toString());
            }
        });

    }

    private void httpText(String urlAddress){

        final TextView textView = (TextView) findViewById(R.id.outText);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =urlAddress;

        // Request a string response from the provided URL.

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                textView.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
                queue.add(stringRequest);

    }
}
