package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // declare variables
    EditText urlField;
    Button connectBtn;
    TextView textView;
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        urlField = findViewById(R.id.urlField);
        connectBtn = findViewById(R.id.connectbtn);
        textView = findViewById(R.id.textView);

        //check network
        if (checkPermissionInternet()) {
            this.toastNoti("No network connection");
        }


    }

    // event function
    public  void clickConnectBtn(View view) {
        // get text from text field
        String url= urlField.getText().toString();

        // TODO: send request and get response as result
        // Initiate new request queue
        RequestQueue queue = Volley.newRequestQueue(this);



        //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // print result on text view
                textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastNoti(error.toString());
            }
        });
        //Add request to Request Queue
        queue.add(stringRequest);
    }
    // Toast
    protected void  toastNoti(String content) {
        Toast toast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    // Check permission
    protected boolean checkPermissionInternet() {
        ConnectivityManager contectMng = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if(contectMng != null) {
            networkInfo = contectMng.getActiveNetworkInfo();
        }else  {
            networkInfo = null;
        }
        return  networkInfo != null && networkInfo.isConnected();
    }
}

