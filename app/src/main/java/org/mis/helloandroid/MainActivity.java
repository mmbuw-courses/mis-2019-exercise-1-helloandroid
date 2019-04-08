package org.mis.helloandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onSendClick(View view){
        //Get text
        //https://stackoverflow.com/questions/7200108/android-gettext-from-edittext-field
        TextInputEditText urlInput = (TextInputEditText)findViewById(R.id.tiet);
        String urlText = urlInput.getText().toString();

        //Check if text was submitted
        if(urlText.length() == 0){
            //https://developer.android.com/guide/topics/ui/notifiers/toasts#java
            Toast.makeText(getApplicationContext(), "Error: No text entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Convert text to URL
        try{
            URL urlURL = new URL(urlText);
            //TODO: Send GET request
            Toast.makeText(getApplicationContext(), "Sending HTTP request.", Toast.LENGTH_SHORT).show();

            //TODO: Parse/display response

        }
        catch (MalformedURLException e){
            Toast.makeText(getApplicationContext(), "Error: Not a valid URL!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
