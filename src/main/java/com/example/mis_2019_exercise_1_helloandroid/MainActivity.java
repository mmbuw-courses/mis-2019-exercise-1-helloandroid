package com.example.mis_2019_exercise_1_helloandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //URL input text field
        final EditText url_text = (EditText) findViewById(R.id.url_text);
        //Convert URL text to string
        final String url = url_text.getText().toString();
        //Button
        Button go_button = (Button) findViewById(R.id.go_button);
        //Text-view to display received response
        final TextView textView = (TextView) findViewById(R.id.text);

        //When button is clicked, will send HTTP request using Volley
        go_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                //Show response from server on the Text-view
                                public void onResponse(JSONObject response) {
                                    textView.setText("Response: " + response.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                //Show error response from server on the Text-view
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();;
                                }
                            }
                    );
                    //Source: "https://developer.android.com/training/volley/simple#java"
                    //Add the request above to Volley's RequestQueue
                    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
                    MySingleton.getInstance(this).getRequestQueue();
                    MySingleton.getInstance(this).getImageLoader();
                } catch(Exception e) {
                    // the error message is displayed here on a snackbar or a toast
                    // Source https://examples.javacodegeeks.com/android/core/ui/toast/android-toast-example/
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}