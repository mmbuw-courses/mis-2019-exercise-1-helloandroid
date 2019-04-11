package com.example.exercise_1;

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
import android.permission.INTERNET;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {
    //variables
    EditText textUrl;
    Button Connect_button;
    TextView viewTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUrl = findViewById(R.id.textUrl);
        Connect_button = findViewById(R.id.Connect_button);
        viewTxt = findViewById(R.id.viewTxt);

        if (InternetpermissionCheck())
        {
            this.Toasty("No network connection");
        }

    }

    protected void  Toasty (String content)
    {
        Toast toast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }


    //https://developer.android.com/training/volley/simple
    // http://blog.saltfactory.net/using-https-on-android/
    //https://www.youtube.com/watch?v=iwyK9S9yMgs&t=520s
    public void clickConnectButton(View viEW)
    {
        String url= textUrl.getText().toString();
        RequestQueue que1 = Volley.newRequestQueue(this);

        StringRequest string_reque = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {
                viewTxt.setText(response.substring(0,650));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toasty.makeText(MainActivity.this, "You can not retrieve the contents");
            }
        });

        queue.add(stringRequest);

    }

//https://developer.android.com/reference/java/net/HttpURLConnection
    //https://www.youtube.com/watch?v=enrPlCWHTb4&t=255s
    //https://www.youtube.com/watch?v=iwyK9S9yMgs&t=520s

    protected boolean InternetpermissionCheck() {
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
