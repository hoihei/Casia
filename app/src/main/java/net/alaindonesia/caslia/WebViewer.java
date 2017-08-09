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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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

    }

    private int countDown=1;
    private int MAX_COUNT_DOWN=20;
    private String repeatUriString = "";

    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(final String uriString){
        final WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String uriString) {
                Toast.makeText(view.getContext(), "No casual browsing " + countDown + "/" + MAX_COUNT_DOWN, Toast.LENGTH_SHORT).show();

                if (countDown >= MAX_COUNT_DOWN){
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    countDown = 1;
                }

                if (repeatUriString.equals(uriString))
                    countDown = countDown + 1;
                else
                    countDown = 1;
                    repeatUriString = uriString;

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url){
                view.getSettings().setJavaScriptEnabled(false);
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

