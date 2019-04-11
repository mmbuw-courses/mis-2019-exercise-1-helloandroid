package com.awesometek.mis1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText urlText = findViewById(R.id.urlText);
        urlText.setText("https://awesometek.com");
        final TextView outputText = findViewById(R.id.outputText);
        outputText.setMovementMethod(new ScrollingMovementMethod());
        final Button connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlText.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                if(url.matches("")) {
                    Toast.makeText(getApplicationContext(), "No URL given!",
                            Toast.LENGTH_LONG).show();
                } else if(!URLUtil.isValidUrl(url)) {
                    Toast.makeText(getApplicationContext(), "Invalid URL!",
                            Toast.LENGTH_LONG).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                outputText.setText("Response is: " + response.substring(0, 500));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    });
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Toast.makeText(getApplicationContext(), "Bye!", Toast.LENGTH_LONG).show();
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
