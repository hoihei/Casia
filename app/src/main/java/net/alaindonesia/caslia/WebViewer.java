package net.alaindonesia.caslia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewer extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    loadUrl(sharedText);
                }
            }
        }


        focus_start_time_count = 1;
        handler = new Handler();
        runnable = new Runnable(){
            public void run() {
                focusCountDown();
            }
        };
        handler.postDelayed(runnable, interval);


    }

    private boolean is_focus_time = false;
    int focus_start_time_max = 5;
    int focus_start_time_count;
    Runnable runnable;
    Handler handler;
    int interval = 1000; // 1 Second

    private void focusCountDown(){

        if (is_start_focus_counting) {
            Toast.makeText(this.getApplicationContext(), "Focus in " + focus_start_time_count + "/" + focus_start_time_max, Toast.LENGTH_SHORT).show();
            focus_start_time_count = focus_start_time_count +1;
        }

        if(focus_start_time_count <= focus_start_time_max) {
            handler.postDelayed(runnable, interval);
        }else {
            Toast.makeText(this.getApplicationContext(), "Focus now, javascript disable, link need many click", Toast.LENGTH_LONG).show();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.getSettings().setDomStorageEnabled(false);
            is_focus_time = true;
            is_start_focus_counting = false;
        }

    }


    private boolean is_start_focus_counting = false;
    private int focus_start_time = 5;
    private int countDown=1;
    private int MAX_COUNT_DOWN=20;
    private String repeatUriString = "";

    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(final String uriString){


        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String uriString) {

                if(is_focus_time) {
                    Toast.makeText(view.getContext(), "Focus time; Need click more: " + countDown + "/" + MAX_COUNT_DOWN, Toast.LENGTH_SHORT).show();

                    if (countDown >= MAX_COUNT_DOWN) {
                        Uri uri = Uri.parse(uriString);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        countDown = 1;

                    }

                    if (repeatUriString.equals(uriString))
                        countDown = countDown + 1;
                    else {
                        countDown = 1;
                        repeatUriString = uriString;
                    }
                }else{
                    view.loadUrl(uriString);
                }

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                is_start_focus_counting = true;
            }
        });


        webView.loadUrl(uriString);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }



}

