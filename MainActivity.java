package com.example.assignmentzero;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;


// Much liberal use of the online reference https://developer.android.com/reference  happened in the writing and rewriting of this code.

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button = findViewById(R.id.goButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                EditText urlInput = (EditText) findViewById(R.id.inputURL);
                String urlString = urlInput.getText().toString();

                WebView theWebView = (WebView) findViewById(R.id.webView);
                theWebView.loadUrl(urlString);

                try {
                    theWebView.loadUrl(urlString);
                }
                catch (Exception e) {
                    Log.d(e.getClass().getCanonicalName(), e.getMessage()); // Log the exception

                }



            }
        });




    }
}
