package com.example.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
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
import java.util.List;

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
        textInput.getEditText().setText("http://www.conanima.cl");

        String textUrl= textInput.getEditText().getText().toString();
        connectBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                httpCall(textInput.getEditText().getText().toString());
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

        //Scrolling
        // https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        textView.setMovementMethod(new ScrollingMovementMethod());

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String urlSource =urlAddress;
        Spannable parsedText;
        // Request a string response from the provided URL.

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //http://android-coding.blogspot.com/2013/09/htmlimagegetter-load-image-from-internet.html
                                Spanned parsedText= Html.fromHtml(response,   new Html.ImageGetter() {

                                    @Override
                                    public Drawable getDrawable(String source) {
                                        LevelListDrawable d = new LevelListDrawable();
                                        source=urlSource+"/"+source;

                                        Toast.makeText(getApplicationContext(), source,
                                                Toast.LENGTH_LONG).show();

                                            // load from internet
                                            Drawable empty = getResources().getDrawable(R.drawable.abc_btn_check_material);;
                                            d.addLevel(0, 0, empty);
                                            d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                                            new ConnectHandler(getApplicationContext(),source,d).execute(textView);


                                        return d;
                                    }

                                }, null);

                                //Change the text
                                textView.setText(parsedText);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
                queue.add(stringRequest);

    }

    //https://stackoverflow.com/questions/3758535/display-images-on-android-using-textview-and-html-imagegetter-asynchronously
    private class ConnectHandler extends AsyncTask<TextView, Void, Bitmap>{

        private LevelListDrawable levelListDrawable;
        private Context context;
        private String source;
        private TextView t;

        public ConnectHandler(Context context, String source, LevelListDrawable levelListDrawable) {
            this.context = context;
            this.source = source;
            this.levelListDrawable = levelListDrawable;
        }


        @Override
        protected Bitmap doInBackground(TextView... params) {
            t = params[0];
            Bitmap bm=null;
            try{
                Log.d("TAG", source);
                URL sourceURL = new URL(source);
                URLConnection urlConnection = sourceURL.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(inputStream);
                bm = BitmapFactory.decodeStream(bufferedInputStream);

            } catch (
                    MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (
                    IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            Drawable d = new BitmapDrawable(context.getResources(), bitmap);
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);

            // Lets calculate the ratio according to the screen width in px
            int multiplier = size.x / bitmap.getWidth();
            Log.d("TAG", "multiplier: " + multiplier);
            levelListDrawable.addLevel(1, 1, d);

            // Set bounds width  and height according to the bitmap resized size
            levelListDrawable.setBounds(0, 0, bitmap.getWidth() * multiplier, bitmap.getHeight() * multiplier);
            levelListDrawable.setLevel(1);
            t.setText(t.getText()); // invalidate() doesn't work correctly...


        }
    }

}
