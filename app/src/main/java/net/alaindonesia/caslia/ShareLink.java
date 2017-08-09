package net.alaindonesia.caslia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ShareLink extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent_opening_link = getIntent();
        Uri uri = intent_opening_link.getData();

        assert uri != null;
        String uriString = uri.toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, uriString);
        i.putExtra(Intent.EXTRA_SUBJECT, uriString);
        startActivity(Intent.createChooser(i, uriString));

        finish();

    }

}

