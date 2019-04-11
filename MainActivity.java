package com.example.coba01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    RequestQueue reqQue;
    private RequestQueue requestQueue;

    private EditText inputText;
    private Button buttonConnect;
    private WebView webView;
    TextView textResult;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reqQue = Volley.newRequestQueue(this);

        inputText = (EditText)findViewById(R.id.inputText);
        buttonConnect = (Button)findViewById(R.id.buttonConnect);
        textResult = (TextView)findViewById(R.id.textResult);
        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new browser());

        buttonConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(inputText.getText().toString().equals("")){
                    //exception handling with Toast. Condition: user did not input anything into the EditText
                    Toast.makeText(MainActivity.this, "Please enter the URL", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //requesting from URL and display it in the textView
                    //source: https://developer.android.com/training/volley/simple
                    String url = inputText.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            textResult.setText(response.substring(0,750));
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            //exception handling with Toast. Condition: cannot retrieve the page source from the url
                            Toast.makeText(MainActivity.this, "Couldn't retrieve content",
                                    Toast.LENGTH_LONG);
                        }
                    });

                    reqQue.add(stringRequest);

                    //to render in a WebView
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);

                }

            }
        });

    }

    private class browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String inputText){
            view.loadUrl(inputText);
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
