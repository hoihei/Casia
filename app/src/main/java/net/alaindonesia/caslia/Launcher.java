package net.alaindonesia.caslia;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Launcher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadData("Nothing to shown in here. This just an app launcher. " +
                        "\nJust click a web link in other app." +
                        "\nThen share/send/open it in any app you want", "text/html", "UTF-8");


        Toast.makeText(this.getApplicationContext(),
                "Nothing to shown in here. This just an app launcher. " +
                        "\nJust click a web link in other app." +
                        "\nThen share/send/open it in any app you want",
                Toast.LENGTH_SHORT).show();
        finish();

        finish();

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }

}




