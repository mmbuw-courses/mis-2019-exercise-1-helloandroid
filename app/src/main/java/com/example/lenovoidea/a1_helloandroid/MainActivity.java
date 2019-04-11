package com.example.lenovoidea.a1_helloandroid;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button button_load, button_image;
    TextView textView_response;
    EditText editText_url;
    ImageView imageView;

    String url= "https://www.google.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_load = (Button) findViewById(R.id.button_load);
        button_image = (Button) findViewById(R.id.button_image);
        textView_response = (TextView) findViewById(R.id.textView_response);
        editText_url = (EditText) findViewById(R.id.editText_url);
        imageView = (ImageView) findViewById(R.id.imageView);

        button_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editText_url.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView_response.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView_response.setText("Error occured " + error.getMessage());
                    }
                });

                imageView.setVisibility(View.INVISIBLE);

                requestQueue.add(stringRequest);
            }

        });

        button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editText_url.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    }
                });

                textView_response.setText("");
                imageView.setVisibility(View.VISIBLE);

                requestQueue.add(imageRequest);
            }
        });
    }
}
