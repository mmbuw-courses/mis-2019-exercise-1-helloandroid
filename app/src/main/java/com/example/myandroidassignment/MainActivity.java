/* Name: Ademola Eric Adewumi */
/* Matric Number: 120769 */

package com.example.myandroidassignment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {


    Button button;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.submitButton);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        /* https://alvinalexander.com/source-code/android/android-how-add-click-listener-button-action-listener */
        /* https://developer.android.com/training/volley/simple? */
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(editText.getContext());
                String url = editText.getText().toString();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // https://developer.android.com/training/basics/firstapp/starting-activity.html
                                // uses or overrides a deprecated API.
                                textView.setText(Html.fromHtml(response));
                                // http://www.worldbestlearningcenter.com/tips/Android-scrollable-TextView.htm
                                textView.setMovementMethod(new ScrollingMovementMethod());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Context context = getApplicationContext();
                        /* https://www.programcreek.com/java-api-examples/index.php?api=com.android.volley.error.VolleyError */
                        CharSequence text = "";
                        /* https://stackoverflow.com/questions/24700582/handle-volley-error */
                        if (error instanceof TimeoutError ) {
                            text = "Timeout Error. Please Try Again";
                        } else if (error instanceof NoConnectionError) {
                            text = "NoConnectionError. Please Try Again";
                        } else if (error instanceof AuthFailureError) {
                            text = "AuthFailureError. Please Try Again";
                        } else if (error instanceof ServerError) {
                            text = "ServerError. Please Try Again";
                        } else if (error instanceof NetworkError) {
                            text = "NetworkError. Please Try Again";
                        } else if (error instanceof ParseError) {
                            text = "ParseError. Please Try Again";
                        } else text = error.getMessage();


                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                /* https://stackoverflow.com/questions/16780294/how-to-print-to-the-console-in-android-studio */
                /* https://stackoverflow.com/questions/28044873/android-non-static-method-cannot-be-referenced-from-static-context-confused */
            }
        });

    }
}
