package com.example.assignment_i;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView resultTextview;
    private EditText url;
    private ImageView searchImageResult;
    private boolean plainText;
    private SwitchCompat isJsonResponse;
    private Button connectButton;
    private Button showImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Source: https://www.youtube.com/watch?v=hOlx11qQSq8
        resultTextview = findViewById(R.id.resultTextView);
        connectButton = findViewById(R.id.search);
        // Source: https://www.youtube.com/watch?v=dFlPARW5IX8
        showImageButton = findViewById(R.id.ShowImage);
        searchImageResult = findViewById(R.id.resultimageView);
        isJsonResponse = findViewById(R.id.toggle_response_type);
        url = findViewById(R.id.url);

        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String currentText = s.toString();
                if (currentText.endsWith(".jpg") || currentText.endsWith(".jpeg") || currentText.endsWith(".png") || currentText.endsWith(".bmp")) {
                    showImageButton.setEnabled(true);
                    connectButton.setEnabled(false);
                } else {
                    showImageButton.setEnabled(false);
                    connectButton.setEnabled(true);
                }
            }
        });

        plainText = true;
        isJsonResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) plainText = false;
                else plainText = true;
            }
        });
    }

    public void showImage(View v) {
        String imageURL = "http://ageofwonders.com/wp-content/uploads/2014/02/joker-face-painting.jpg";
//        String imageURL = String.valueOf(url.getText());
        Picasso.get().load(imageURL).into(searchImageResult);
    }

    public void searchURL(View v) {
        String server_url = url.getText().toString();
        resultTextview.setText("");
        server_url = redefineURL(server_url);
        if (plainText) {
            //Source: https://developer.android.com/training/volley/simple
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET,
                            server_url,
                            getPlainTextResponse,
                            getError);
            requestQueue.add(stringRequest);
        } else {
            Context mContext;
            mContext = getApplicationContext();

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET,
                            server_url,
                            null,
                            getJsonResponse,
                            getError);
            requestQueue.add(jsonObjectRequest);
        }
        url.setText("");
    }

    //Source: https://android--examples.blogspot.com/2017/02/android-volley-json-object-request.html
    Response.Listener<JSONObject> getJsonResponse = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray array = response.getJSONArray("students");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject student = array.getJSONObject(i);
                    String firstName = student.getString("firstname");
                    String lastName = student.getString("lastname");
                    String age = student.getString("age");
                    resultTextview.append(firstName + " " + lastName + "\nage : " + age);
                    resultTextview.append("\n\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

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

    Response.Listener<String> getPlainTextResponse = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            resultTextview.setText(response);
        }
    };

    private String redefineURL(String url) {
        String newString = "";
        if (url.charAt(0) == 'w') {
            newString = "https://" + url;
        } else if (url.substring(0, 5) != "https") {
            int index = url.indexOf("http");
            int i = 0;
            boolean addS = true;
            while (i < url.length()) {
                if (i == (index + 4) && addS) {
                    newString += 's';
                    i = 4;
                    addS = false;
                } else {
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
            //resultTextview.setText(server_url);

            //Source: https://developer.android.com/training/volley/simple
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, getResonse, getError);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                 new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       resultTextview.setText(response);
                       requestQueue.stop();
                    }
                 },
                 new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       resultTextview.setText(": Something goes wrong");
                       error.printStackTrace();
                       requestQueue.stop();
                    }
                 });
            requestQueue.add(stringRequest);
            url.setText("");
         }
      });*/
