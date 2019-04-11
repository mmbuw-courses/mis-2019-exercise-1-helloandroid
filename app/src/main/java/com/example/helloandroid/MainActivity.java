package com.example.helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    EditText URLText;
    Button connectBtn;
    TextView resultText;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        URLText =(EditText) findViewById(R.id.URLText);
        connectBtn=(Button)findViewById(R.id.connectBtn);
        resultText=(TextView)findViewById(R.id.resultText);
        resultText.setMovementMethod(new ScrollingMovementMethod());/*To make TextView SCrollable
        https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html*/

        /*for Viewing webpage in WebView and not in other browsers
        https://developer.android.com/guide/webapps/webview*/
        webView = (WebView)findViewById(R.id.URLWebView);
        webView.setWebViewClient(new WebViewClientImpl());

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL = URLText.getText().toString();
                Log.i("info : ","URL is "+URL);

                final RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                resultText.setText(response.substring(0,1000));
                                webView.loadUrl(URL);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /* error handling in volley
                        https://stackoverflow.com/questions/24700582/handle-volley-error*/
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(MainActivity.this,
                                    MainActivity.this.getString(R.string.error_network_timeout),
                                    Toast.LENGTH_LONG).show();
                        }  else if (error instanceof ServerError) {
                            Toast.makeText(MainActivity.this,
                                    MainActivity.this.getString(R.string.server_error),
                                    Toast.LENGTH_LONG).show();
                        } else if (error.getCause() instanceof MalformedURLException) {
                            Toast.makeText(MainActivity.this,
                                    MainActivity.this.getString(R.string.uri_error),
                                    Toast.LENGTH_LONG).show();
                        } else if (error.getCause() instanceof RuntimeException ) {
                            Toast.makeText(MainActivity.this,
                                    MainActivity.this.getString(R.string.uri_error),
                                    Toast.LENGTH_LONG).show();
                        }
                        requestQueue.stop();

                    }
                });
                requestQueue.add(stringRequest);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
