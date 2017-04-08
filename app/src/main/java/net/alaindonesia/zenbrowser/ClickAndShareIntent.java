package net.alaindonesia.zenbrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ClickAndShareIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        String uriString = "";

        if (uri != null)
            uriString = uri.toString();
        else
            finish();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Share/Send/Open the link");
        i.putExtra(Intent.EXTRA_TEXT, uriString);
        startActivity(Intent.createChooser(i, "Share/Send/Open the link"));

        finish();


    }

}
