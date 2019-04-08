package com.example.misexc01;

//Jawad Ahmed
//119150

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText urlText;
    TextView outputText;
    String result;
    Boolean check = true;

    public void buttonClick(View view){
        downloadTask task = new downloadTask();
        try {
            task.execute(urlText.getText().toString());
            //task.execute("https://en.wikipedia.org/wiki/Bart_Simpson#/media/File:Bart_Simpson_200px.png");
        }
        catch (Exception e){
            Toast.makeText(this, "Oh no! Something went wrong. Kindly check URL (Check if http(s):// is added before www)", Toast.LENGTH_LONG).show();
        }
    }

    public void webView(View view){
        if((outputText.getText().toString() == "")||(result == null)){
            Toast.makeText(this, "Please connect to a website", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getApplicationContext(), ViewContent.class);
            intent.putExtra("Content", result);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.urlText);
        outputText = findViewById(R.id.outputText);
        outputText.setMovementMethod(new ScrollingMovementMethod());
    }
    public class downloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

               while(data!=-1){
                    char currentData = (char) data;
                    result+=currentData;
                    data = reader.read();

                }

            } catch (MalformedURLException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        check=false;
                        Toast.makeText(MainActivity.this, "URL not properly formed, please check again!", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        check=false;
                        Toast.makeText(MainActivity.this, "Oops! Something went wrong, couldn't get connection. Are you sure website exists?", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (check) Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();
            check=true;
            outputText.setText(result);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (check) Toast.makeText(MainActivity.this, "Loading content, please wait!", Toast.LENGTH_SHORT).show();
        }
    }
}

/*
References:

https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
New method learned to make the TextView scrollable... Preferred to use TextView over Multiline EditText to stop the user from changing the output

https://stackoverflow.com/questions/38865870/download-web-content-with-asynctask
referred from here because of crashing App

https://stackoverflow.com/questions/2837676/how-to-raise-a-toast-in-asynctask-i-am-prompted-to-used-the-looper
Referred from here to make the Toasts run on UI thread while trying to execute them from the doInBackground


 */