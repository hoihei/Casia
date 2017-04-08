package net.alaindonesia.zenbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


public class MainActivityZen extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String uriString = intent.getStringExtra("URI");

        if(uriString == null || uriString.equals("")) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
            finish();
        }

//        webview = new WebView(this);
//        setContentView(webview);
//        webview.setWebViewClient(new ZenWebViewClient());
//        setUpWebView(webview);
//        webview.loadUrl(uriString);


        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        i.putExtra(Intent.EXTRA_TEXT, uriString);
        startActivity(Intent.createChooser(i, "Share URL"));

    }


    static WebView setUpWebView(WebView webview){

        WebSettings settings = webview.getSettings();
        settings.setDefaultTextEncodingName("utf-32");

        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);

        webview.setLongClickable(true);
        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO: Handle link long click listener
                Toast.makeText(view.getContext(), "TODO: Handle link long click listener", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        return webview;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}


