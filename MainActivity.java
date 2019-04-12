package com.example.assignmentzero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;


// Much liberal use of the online reference https://developer.android.com/reference  happened in the writing and rewriting of this code.
// Many examples were referenced at stackoverflow.com


//Class that was built automatically when the project was created.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Access the button
        final Button button = findViewById(R.id.goButton);
        //Set the button's event listener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //Get the reference to the textbox and then get the string from it.
                EditText urlInput = (EditText) findViewById(R.id.inputURL);
                String urlString = urlInput.getText().toString();

                //Get a reference to the web view
                WebView theWebView = (WebView) findViewById(R.id.webView);

                //Put the URL Loading in an exception handler so that we can catch any exceptions
                try {
                    //Attempt to load the requested page
                    theWebView.loadUrl(urlString);
                }
                catch (Exception e) {
                    //If it fails then post the exception to the error log
                    Log.d(e.getClass().getCanonicalName(), e.getMessage()); // Log the exception

                }



            }
        });




    }
}
