package com.example.lenovoidea.a1_helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button button_load;
    TextView textView_response;
    EditText editText_url;

    String url= "https://www.google.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_load = (Button) findViewById(R.id.button_load);
        textView_response = (TextView) findViewById(R.id.textView_response);
        editText_url = (EditText) findViewById(R.id.editText_url);


        button_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editText_url.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView_response.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView_response.setText("Error occured " + error.getMessage());
                    }
                });

                requestQueue.add(stringRequest);
            }

        });
    }
}
