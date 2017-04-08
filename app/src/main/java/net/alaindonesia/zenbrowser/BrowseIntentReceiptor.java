package net.alaindonesia.zenbrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BrowseIntentReceiptor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Uri uri = intent.getData();

        Intent mainActivityIntent = new Intent(this, MainActivityZen.class);
        if (uri != null)
            mainActivityIntent.putExtra("URI", uri.toString());
        else
            mainActivityIntent.putExtra("URI", "");
        startActivity(mainActivityIntent);
        finish();
    }

}
