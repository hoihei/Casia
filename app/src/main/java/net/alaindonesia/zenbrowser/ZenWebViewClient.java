package net.alaindonesia.zenbrowser;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


//DONE: 20170408->0408 override back button to previous site
//DONE: 20170408->0408 disable javascript navigation
//TODO: 20170408 share button

class ZenWebViewClient extends WebViewClient {

    private int numberOfLinkVisited;

    ZenWebViewClient(){
        numberOfLinkVisited = 0;

    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return handleUrl(view,  Uri.parse(url));
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
        return handleUrl(view, request.getUrl());
    }

    private boolean handleUrl(WebView view, Uri uri){
        numberOfLinkVisited = numberOfLinkVisited + 1;

        int maxNumberLinkVisit = 1;

        if (numberOfLinkVisited > maxNumberLinkVisit){
            numberOfLinkVisited = maxNumberLinkVisit;
            String text = "ZenBrowsing: Browsing further disallowed";
            Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG);
            return true;

        }
        return false;

    }

}
