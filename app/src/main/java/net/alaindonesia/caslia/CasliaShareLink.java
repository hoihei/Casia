package net.alaindonesia.caslia;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CasliaShareLink extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent_opening_link = getIntent();
        Uri uri = intent_opening_link.getData();

        assert uri != null;
        String uriString = uri.toString();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, uriString);


        int length = uriString.length();
        int maxLength = 50;
        if (length > maxLength){
            length = maxLength;
        }
        String uriSubString = uriString.substring(0, length);
        Intent intentChooser = Intent.createChooser(sendIntent, uriSubString);
        startActivity(intentChooser);

        finish();

    }


}

