package com.example.exercise_1_mis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Toast;
import android.view.Gravity;


public class MainActivity extends AppCompatActivity {

    //variables from layouts

    EditText urlText;
    Button connectButton;
    TextView urlTextView;
    WebView websiteView;
    //ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        urlText = findViewById(R.id.urlText);
        connectButton = findViewById(R.id.connectButton);
        urlTextView = findViewById(R.id.urlTextView);
        websiteView = findViewById(R.id.websiteView);


        }






    public void connectButtonnClick(View view){

        // Instantiate the RequestQueue.
        //https://developer.android.com/training/volley/simple.html
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlText.getText().toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        urlTextView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //urlTextView.setText(error.toString());
                //imageView.getDisplay();

                ToastErrorhandle(error.toString());

               // Toast toast = new Toast(getApplicationContext());
              //  toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
              //  toast.setDuration(Toast.LENGTH_LONG);
                //toast.setView(layout);
             //   toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        websiteView.loadUrl(url);

    }
    //https://developer.android.com/guide/topics/ui/notifiers/toasts
    private void  ToastErrorhandle(String errorMessage) {

        int duration1 = Toast.LENGTH_SHORT;
        int duration2 = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(MainActivity.this,errorMessage, duration2);

        toast.setGravity(Gravity.BOTTOM,0,750);
        toast.show();
    }


}
