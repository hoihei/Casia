package net.alaindonesia.caslia;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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
