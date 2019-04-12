package com.example.lab01;

        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.method.ScrollingMovementMethod;
        import android.util.Log;
        import android.view.Display;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.NoConnectionError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.ServerError;
        import com.android.volley.TimeoutError;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.squareup.picasso.Picasso;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

        import java.net.MalformedURLException;
        import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    Button btngo;
    EditText txtfld;
    TextView txtvu;
    String URL;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Learned how to give references to elements in the following:
            https://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field */
        setContentView(R.layout.activity_main);
        btngo = (Button)findViewById(R.id.btnGO);
        txtfld = (EditText)findViewById(R.id.txtfldURL);
        txtvu = (TextView)findViewById(R.id.txtfld);
        txtvu.setMovementMethod(new ScrollingMovementMethod()); //learned from: https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android

        btngo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if("".equals(txtfld.getText().toString())) {
                    /*learned toast() from the following
                        https://www.youtube.com/watch?v=mAJeK283j0I */
                    Toast.makeText(getApplicationContext(), "Please enter a URL.", Toast.LENGTH_LONG).show();
                }
                else{
                    URL = txtfld.getText().toString();
                    /*for volley and it's error handling, I took reference and help from Saif Khan in MIS 2019
                        He took reference from the following: https://stackoverflow.com/questions/24700582/handle-volley-error */
                    Log.i("info : ","URL is "+URL);
                    final RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Enjoy.", Toast.LENGTH_LONG).show();
                                    new scratch().execute();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(MainActivity.this,
                                        MainActivity.this.getString(R.string.error_network_timeout),
                                        Toast.LENGTH_LONG).show();
                            }  else if (error instanceof ServerError) {
                                Toast.makeText(MainActivity.this,
                                        MainActivity.this.getString(R.string.server_error),
                                        Toast.LENGTH_LONG).show();
                            } else if (error.getCause() instanceof MalformedURLException) {
                                Toast.makeText(MainActivity.this,
                                        MainActivity.this.getString(R.string.uri_error),
                                        Toast.LENGTH_LONG).show();
                            } else if (error.getCause() instanceof RuntimeException ) {
                                Toast.makeText(MainActivity.this,
                                        MainActivity.this.getString(R.string.uri_error),
                                        Toast.LENGTH_LONG).show();
                            }
                            requestQueue.stop();

                        }
                    });
                    requestQueue.add(stringRequest);

                }
            }
        });
    }




    public class scratch extends AsyncTask<Void,Void,Void>{
        String text,image;
        ArrayList list = new ArrayList();
        //ProgressDialog pg = new ProgressDialog(MainActivity,this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            count++;
            //pg = new progressDialog(MainActivity.this);
            //pg.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                /*Learned scraping webpages via JSoup form:
                    https://www.youtube.com/watch?v=x-VmYZGPnWc */
                Document doc = Jsoup.connect(URL).get();
                text = doc.text();
                Elements img = doc.getElementsByTag("img");
                for(Element i : img){
                    image = i.absUrl("src");
                    list.add(image);
                }
                Log.d("images links", list.toString());
            }catch(Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtvu.setText(text);
            LinearLayout layout = (LinearLayout)findViewById(R.id.linearvu);
            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).toString()==""){
                    i++;
                }
                else {
                    /*add imageview dynamically. (this was my last choice as I had spent too much time trying to get recycler view adapter class to work):
                        https://stackoverflow.com/questions/15356473/create-imageviews-dynamically-inside-a-loop
                     */
                    ImageView img = new ImageView(MainActivity.this);
                    Display display = getWindowManager().getDefaultDisplay();
                    img.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    img.setAdjustViewBounds(true);
                    layout.addView(img);
                    /* three references used to get Picasso to work, they have been stated below:
                        https://www.youtube.com/watch?v=yl4FLNJ96vw
                        https://github.com/square/picasso
                        https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library
                     */
                    Picasso.get().load(list.get(i).toString()).into(img);
                }
            }

        }
    }

}
