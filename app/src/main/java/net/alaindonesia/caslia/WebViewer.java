package net.alaindonesia.caslia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewer extends Activity {

    WebView webView;

    final private int FOCUS_TIME_MAX = 20; // seconds
    final private int CLICK_NEED_MAX = 20;

    private int click_countdown = 1;
    private String repeatUriString = "";
    private boolean is_start_focus_countdown;
    private boolean is_focus_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        is_start_focus_countdown = false;
        is_focus_time = false;

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (sharedText != null) {
//                        loadUrl(sharedText);

                        webView.loadUrl(sharedText);
                    }
            }
        }


//        int start_focus_time_countdown = 0;
//        while (start_focus_time_countdown <= FOCUS_TIME_MAX){
//            if (is_start_focus_countdown){
//
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException ignored) {
//                }
//
//                start_focus_time_countdown +=1;
//                Toast.makeText(webView.getContext(), "Focus on " + start_focus_time_countdown + "/"
//                        + FOCUS_TIME_MAX, Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        is_focus_time = true;
//        webView.getSettings().setJavaScriptEnabled(false);

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(final String uriString){


        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String uriString) {
//                if (is_focus_time) {
                    Toast.makeText(view.getContext(), "Focus time, click more " + click_countdown + "/" + CLICK_NEED_MAX, Toast.LENGTH_SHORT).show();

                    if (click_countdown >= CLICK_NEED_MAX) {
                        Uri uri = Uri.parse(uriString);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        click_countdown = 1;
                    }

                    if (repeatUriString.equals(uriString))
                        click_countdown = click_countdown + 1;
                    else
                        click_countdown = 1;
                    repeatUriString = uriString;

                    return true;
//                }else{
//                    return false;
//                }
            }

            @Override
            public void onPageFinished(WebView view, String url){
                is_start_focus_countdown = true;

            }
        });

//
//        webView.loadUrl(uriString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }



}

