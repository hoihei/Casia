package net.alaindonesia.caslia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


public class Launcher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this.getApplicationContext(),
                "Nothing to shown in here. This just an app launcher. " +
                        "\nJust click a web link in other app." +
                        "\nThen share/send/open it in any app you want",
                Toast.LENGTH_SHORT).show();
        finish();

    }


}


