package net.alaindonesia.clickandshare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ClickAndShareIntent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        String uriString = "";
        String title = "";

        if (uri != null) {
            uriString = uri.toString();
            ProceedLink proceedLink = new ProceedLink();
            proceedLink.setUriString(uriString);
            proceedLink.execute();
            while(true){
                title = proceedLink.getTitle();
                if(title != null && !title.isEmpty())
                    break;

            }
        } else
            finish();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, uriString);

        if (title.isEmpty()) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Share/Send/Open the link");
            startActivity(Intent.createChooser(i, "Share/Send/Open the link"));
        }else{
            i.putExtra(Intent.EXTRA_SUBJECT, title);
            startActivity(Intent.createChooser(i, title));
        }

        finish();


    }

}

class ProceedLink extends AsyncTask<Void, Void, String> {

    private String uriString;
    private String title ="";

    ProceedLink(){
        title ="";
        uriString = "";
    }

    @Override
    protected String doInBackground(Void... params) {

        Document doc;

        if (uriString != null) {
            try {
                doc = Jsoup.connect(uriString).get();
                title = doc.title();
                System.out.print(title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return title;
    }


    @Override
    protected void onPostExecute(String result) {
        //if you had a ui element, you could display the title
    }

    void setUriString(String uriString) {
        this.uriString = uriString;
    }

    String getTitle() {
        return title;
    }
}
