package org.mis.helloandroid;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.net.MalformedURLException;
import java.net.URL;

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
        TextInputEditText urlInput = findViewById(R.id.tiet);
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

            //Send GET request
            Toast.makeText(getApplicationContext(), "Sending HTTP request.", Toast.LENGTH_SHORT).show();

            //https://developer.android.com/training/volley/simple.html
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest requestString = new StringRequest(Request.Method.GET, urlURL.toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();

                            //Print out response
                            TextView httpResponse = findViewById(R.id.tv);
                            //https://stackoverflow.com/a/3256305/6118088
                            httpResponse.setMovementMethod(new ScrollingMovementMethod());
                            httpResponse.setText(response);

                            //Parse response
                            WebView htmlOut = findViewById(R.id.wv);
                            //https://stackoverflow.com/a/8987637/6118088
                            htmlOut.loadData(response, "text/html; charset=utf-8", "UTF-8");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError f) {

                            //Distinguish between different errors
                            if(f.toString().startsWith("com.android.volley.NoConnectionError")){
                                Toast.makeText(getApplicationContext(), "Error: No network connection!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error: " + f.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
            requestQueue.add(requestString);

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
