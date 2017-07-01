package net.alaindonesia.caslia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

// TODO: no need to wait

public class CasliaIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setVisible(true);
        setContentView(R.layout.webview);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        String uriString = "";

        if (uri != null) {
            uriString = uri.toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, uriString);
            i.putExtra(Intent.EXTRA_SUBJECT, uriString);
            startActivity(Intent.createChooser(i, uriString));
        }

        finish();
    }

}

