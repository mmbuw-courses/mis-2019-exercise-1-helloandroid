package com.example.assignment_i;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

   Button search;
   TextView textView;
   EditText url;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textView = (TextView) findViewById(R.id.textView);
      url = (EditText) findViewById(R.id.url);
      search = (Button) findViewById(R.id.search);

      //final String server_url = "https:/www.google.com";
      //final String server_url = "https://api.ipify.org/?format=json";

      //url.setText(server_url);

      search.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final String server_url = url.getText().toString();
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                 new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       textView.setText(response);
                       requestQueue.stop();
                    }
                 },
                 new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       textView.setText(server_url + ": Something goes wrong");
                       error.printStackTrace();
                       requestQueue.stop();
                    }
                 });
            requestQueue.add(stringRequest);
         }
      });
   }
}