package com.example.misexc01;

//Jawad Ahmed
//119150

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
    //http://www.google.com

    public void buttonClick(View view){
        downloadTask task = new downloadTask();
        //System.out.println(urlText.getText().toString());
        try {
            task.execute(urlText.getText().toString());
            //task.execute("https://en.wikipedia.org/wiki/Bart_Simpson#/media/File:Bart_Simpson_200px.png");
        }
        catch (Exception e){
            Toast.makeText(this, "Oh no! Something went wrong. Kindly check URL (Check if http(s):// is added before www)", Toast.LENGTH_LONG).show();
        }
        //task.execute("http://www.google.com");
        //https://stackoverflow.com/questions/38865870/download-web-content-with-asynctask referred from here because of crashing App
        //task.execute("https://www.ecowebhosting.co.uk");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.urlText);
        outputText = findViewById(R.id.outputText);
        /*https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        New method learned to make the TextView scrollable... Preferred to use TextView over Multiline EditText to stop the user from changing the output*/
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
                    //System.out.println("Reading");
                }
                //System.out.println(result);
            } catch (MalformedURLException e) {
                Toast.makeText(MainActivity.this, "URL not properly formed, please check again!", Toast.LENGTH_LONG).show();
               // e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Oops! Something went wrong, couldn't get connection. Are you sure website exists?", Toast.LENGTH_LONG).show();
               // e.printStackTrace();
            } catch (Exception e){
                Toast.makeText(MainActivity.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
               // e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            outputText.setText(result);
        }
    }
}