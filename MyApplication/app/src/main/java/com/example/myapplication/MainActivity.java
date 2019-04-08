package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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
StringRequest strRequest;
RequestQueue queue;
String url;
Button btnRequest;
private WebView webView;
private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRequest = (Button)findViewById(R.id.buttonRequest);
        webView = (WebView)findViewById(R.id.textView);
        textInput = (EditText)findViewById(R.id.textInput);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAndRequestResponse();
            }
        }
        );
    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        queue = Volley.newRequestQueue(this);
        //String Request initialized
        url = textInput.getText().toString();
        //url = "https://www.uni-weimar.de";
        strRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                webView.loadData(response.toString(),"text/html; charset=utf-8", "utf-8");
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :" + error.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        });

        queue.add(strRequest);
    }
}