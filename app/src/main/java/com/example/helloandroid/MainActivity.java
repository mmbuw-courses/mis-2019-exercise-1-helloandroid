package com.example.helloandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import android.text.method.ScrollingMovementMethod;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayList<Bitmap> bitmapsList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Text Input Object
        final TextInputLayout textInput = (TextInputLayout) findViewById(R.id.URLInput);
        final Button connectBtn = (Button)  findViewById(R.id.bringTextBtn);

        //Set a default URL in the input text
        textInput.getEditText().setText("https://www.uni-weimar.de");

        String textUrl= textInput.getEditText().getText().toString();
        connectBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(checkConnection()) {
                    httpCall(textInput.getEditText().getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "There is no network",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //https://developer.android.com/training/volley/simple
    private void httpCall(String urlAddress){
        final String url=urlAddress;
        final TextView textView = (TextView) findViewById(R.id.outText);

        //Make the text view Scrollable
        // https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        textView.setMovementMethod(new ScrollingMovementMethod());

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String urlSource =urlAddress;
        Spannable parsedText;

        // Request a string response from the provided URL.

                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlAddress,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //ImageGetter is a Interface,
                                //http://android-coding.blogspot.com/2013/09/htmlimagegetter-load-image-from-internet.html
                                Spanned parsedText= Html.fromHtml(response,   new Html.ImageGetter() {

                                    @Override
                                    public Drawable getDrawable(String source) {
                                        LevelListDrawable d = new LevelListDrawable();
                                        source=urlSource+"/"+source;

                                            // load from internet
                                            Drawable empty = getResources().getDrawable(R.drawable.abc_btn_check_material);;
                                            d.addLevel(0, 0, empty);
                                            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                                            //AzyncImageLoaderHandler is a class that's extends from Azync, there was and error
                                            // trying to generate a new treath in main activity, according to documentation
                                            //is necessary to use Azync and handle the downloading of the images asynchronous
                                            new AzyncImageLoaderHandler(getApplicationContext(),source,d).execute(textView);


                                        return d;
                                    }

                                }, new MyHtmlTagHandler());

                                //Change the output text with some html formating and images
                                textView.setText(parsedText);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "URL not FOUND",
                                Toast.LENGTH_LONG).show();
                    }
                });

        // Add the request to the RequestQueue.
                queue.add(stringRequest);
    }

    //Function to check connection before making the request
    //https://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else{
            return false;
        }
    }

    //
    //https://stackoverflow.com/questions/3758535/display-images-on-android-using-textview-and-html-imagegetter-asynchronously
    private class AzyncImageLoaderHandler extends AsyncTask<TextView, Void, Bitmap>{

        private LevelListDrawable levelListDrawable;
        private Context context;
        private String source;
        private TextView t;

        //Constructor to receive the main variables to then output the HTML format in textView
        public AzyncImageLoaderHandler(Context context, String source, LevelListDrawable levelListDrawable) {
            this.context = context;
            this.source = source;
            this.levelListDrawable = levelListDrawable;
        }


        @Override
        protected Bitmap doInBackground(TextView... params) {
            t = params[0];
            Bitmap bm=null;
            try{

                URL sourceURL = new URL(source);
                URLConnection urlConnection = sourceURL.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(inputStream);
                bm = BitmapFactory.decodeStream(bufferedInputStream);

            } catch (
                    MalformedURLException e) {
                Log.e("ERROR",e.toString());
            } catch (
                    IOException e) {
                Log.e("ERROR",e.toString());
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            //There where
            if (bitmap!=null){
                try {
                    Drawable d = new BitmapDrawable(context.getResources(), bitmap);
                    Point size = new Point();
                    getWindowManager().getDefaultDisplay().getSize(size);

                    // Lets calculate the ratio according to the screen width in px
                    int multiplier = size.x / bitmap.getWidth();
                    levelListDrawable.addLevel(1, 1, d);

                    // Set bounds width  and height according to the bitmap resized size
                    levelListDrawable.setBounds(0, 0, bitmap.getWidth() * multiplier, bitmap.getHeight() * multiplier);
                    levelListDrawable.setLevel(1);
                    t.setText(t.getText()); // invalidate() doesn't work correctly...
                }catch (Error error){
                    Log.e("ERROR","Image Process:"+error);
                }
            }else{
                Log.e("ERROR","CORRUPT URL IMAGE:"+source);
                }
            }

    }

}
