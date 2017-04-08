package net.alaindonesia.zenbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


public class ClickAndShareLauncher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this.getApplicationContext(),
                "Nothing to shown in here. This just an app launcher. " +
                        "\nJust click an http/https link in other app." +
                        "\nThen share/send/open it in any app you want",
                Toast.LENGTH_LONG).show();
        finish();

    }


}


