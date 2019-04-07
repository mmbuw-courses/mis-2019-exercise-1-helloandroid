package com.example.assignment_i;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
   // Source: https://www.youtube.com/watch?v=dFlPARW5IX8
   private Button search;
   private TextView textView;
   private EditText url;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      textView = findViewById(R.id.textView);
      url = findViewById(R.id.url);
      //Source: https://www.youtube.com/watch?v=hOlx11qQSq8
      search = findViewById(R.id.search);
   }

   public void searchURL(View v){
      String server_url = url.getText().toString();
      server_url = redefineURL(server_url);

      //Source: https://developer.android.com/training/volley/simple
      final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
      StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, getResonse, getError);
     requestQueue.add(stringRequest);
     url.setText("");
   }

   //Source: https://stackoverflow.com/questions/25994514/volley-timeout-error
   Response.ErrorListener getError = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
         if (error instanceof NetworkError) {
            Toast.makeText(MainActivity.this,
                 "No internet connection",
                 Toast.LENGTH_LONG).show();
         } else if (error instanceof ServerError) {
            Toast.makeText(MainActivity.this,
                 "Server not found",
                 Toast.LENGTH_LONG).show();
         } else if (error instanceof AuthFailureError) {
            Toast.makeText(MainActivity.this,
                 "authentication is missing",
                 Toast.LENGTH_LONG).show();
         } else if (error instanceof TimeoutError) {
            Toast.makeText(MainActivity.this,
                 "Oops. Timeout error!",
                 Toast.LENGTH_LONG).show();
         }
      }
   };

   Response.Listener<String> getResonse = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
         textView.setText(response);
      }
   };

   private String redefineURL(String url) {
      String newString = "";
      if(url != "https")
      {
         int index = url.indexOf("http");
         int i = 0;
         boolean addS = true;
         while(i<url.length()){
            if(i == (index + 4) && addS) {
               newString += 's';
               i = 4;
               addS = false;
            }
            else {
               newString += url.charAt(i);
               ++i;
            }
         }
      }
      return newString;
   }
}



/*
      search.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String server_url = url.getText().toString();
            server_url = redefineURL(server_url);
            //textView.setText(server_url);

            //Source: https://developer.android.com/training/volley/simple
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, getResonse, getError);


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
                       textView.setText(": Something goes wrong");
                       error.printStackTrace();
                       requestQueue.stop();
                    }
                 });
            requestQueue.add(stringRequest);
            url.setText("");
         }
      });*/
