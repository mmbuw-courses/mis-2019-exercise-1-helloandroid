package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
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
StringRequest strRequest;
RequestQueue queue;
private WebView webView;
private TextView textView;
private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize all resources
        Button btnRequest = (Button) findViewById(R.id.buttonRequest);
        webView = (WebView)findViewById(R.id.webView);
        textView = (TextView)findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textInput = (EditText)findViewById(R.id.textInput);

        //Basic layout for request/respoonse structure from https://abhiandroid.com/programming/volley
        //Create onClick listener for the button to register a buttonclick
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendAndRequestResponse();
            }
        }
        );
    }

    private void sendAndRequestResponse() {
        //create a new request queue. It manages worker threads for networking operations
        queue = Volley.newRequestQueue(this);
        //Read the content of the EditText box
        final String url = textInput.getText().toString();

        //Create a request to add to the queue. On a successful respnse, load the data into a webViewClient and display the plain HTML response in a textView
        strRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);
                //webView.loadData(response.toString(),"text/html; charset=utf-8", "utf-8");
                textView.setText(response.toString());
                          }
        }, //On an error, display the message in the toast() function
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :" + error.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        });

        //add the request to the Volley RequestQueue.
        queue.add(strRequest);
    }
}