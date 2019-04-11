package com.example.exercise_1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    //Additional code
    String url_str = new String();
    Button submit_button;
    EditText input_text;
    WebView web_view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Additional Code
        submit_button = findViewById(R.id.button);
        input_text = findViewById(R.id.editText);
        web_view = findViewById(R.id.web_view);
        WebSettings setup = web_view.getSettings();
        setup.setUseWideViewPort(true);
        setup.setLoadWithOverviewMode(true);

        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input_manage =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                input_manage.hideSoftInputFromWindow(input_text.getWindowToken(), 0);

                url_str = input_text.getText().toString();
                if(url_str.length() == 0){
                    toast_mes("Please input an URL here");
                }else {
                    web_view.loadUrl("about:blank");
                    if (!url_str.toLowerCase().startsWith("http://") && !url_str.toLowerCase().startsWith("https://")) { //from add_http
                        url_str = "https://" + url_str;
                    }
                    web_view.loadUrl(url_str);
                    web_view.requestFocus();

                }
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void toast_mes(String msg) {
        Toast text_mes = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        text_mes.show();
    }

}