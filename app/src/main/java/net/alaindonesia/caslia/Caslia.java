package net.alaindonesia.caslia;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Caslia extends Activity {

    private boolean isFocusTime = false;
    int focusStartTimerMax = 10;
    int focusStartTimerCount;
    Runnable runnable;
    Handler handler;
    int interval = 1000; // 1 Second

    private boolean isStartFocusCounting;

    private boolean isShowCounterToastNotification = true;

    private int countDownClicking = 1;
    private int MAX_COUNT_DOWN_CLICKING = 20;

    private String repeatUriString = "";

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        boolean isLoadDefault = false;

        Intent intent = getIntent();

        if (intent != null) {

            String action = intent.getAction();

            if (!Intent.ACTION_MAIN.equals(action)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                Uri uri = intent.getData();
                if (sharedText != null) {
                    loadUrl(sharedText);
                } else if (uri != null) {
                    loadUrl(uri.toString());
                } else {
                    isLoadDefault = true;
                }
            }else {
                isLoadDefault = true;
            }
        }else {
            isLoadDefault = true;
        }

        if (isLoadDefault){
//            loadUrl("https://google.com");
            startActivity(new Intent(Intent.ACTION_WEB_SEARCH));
//            finish();
        }

        focusStartTimerCount = 0;
        handler = new Handler();
        runnable = new Runnable(){
            public void run() {
                focusCountDownTimer();
            }
        };
        handler.postDelayed(runnable, interval);

//        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(final String uriString){

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);



        Intent resultIntent = new Intent(this.getApplicationContext(), Caslia.class);
        resultIntent.setAction("android.intent.action.MAIN");
        resultIntent.addCategory("android.intent.category.LAUNCHER");
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );



        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_announcement)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(uriString);
        notification.setContentIntent(resultPendingIntent);


        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1, notification.build());


        webView.setWebViewClient(new CasliaWebClient());

        webView.loadUrl(uriString);


    }


    private void focusCountDownTimer(){


        if (isStartFocusCounting) {
            focusStartTimerCount = focusStartTimerCount + 1;

            makeToastNotification("Focus in " + focusStartTimerCount + "/" + focusStartTimerMax);

        }

        if(focusStartTimerCount <= focusStartTimerMax) {
            handler.postDelayed(runnable, interval);
        }else {

            makeToastNotification("Focus now, javascript disable, link need many click");

            WebView webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.getSettings().setDomStorageEnabled(false);
            isFocusTime = true;
            isStartFocusCounting = false;
        }

    }

    private void makeToastNotification(String text) {
        if(isShowCounterToastNotification) {
            if (this.toast != null) this.toast.cancel();
            this.toast = Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_SHORT);
            this.toast.show();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }

    class CasliaWebClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        public boolean shouldOverrideUrlLoading(WebView view, String uriString) {
            focusWebView(view, uriString);
            return true;
        }


        @Override
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest){
            focusWebView(view, webResourceRequest.getUrl().toString());
            return true;
        }

        private void focusWebView(WebView view, String uriString){
            if(isFocusTime) {
                makeToastNotification("Focus time; Need click more: " + countDownClicking + "/" + MAX_COUNT_DOWN_CLICKING);


                if (countDownClicking >= MAX_COUNT_DOWN_CLICKING) {
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    countDownClicking = 1;

                }

                if (repeatUriString.equals(uriString))
                    countDownClicking = countDownClicking + 1;
                else {
                    countDownClicking = 1;
                    repeatUriString = uriString;
                }
            }else{
                view.loadUrl(uriString);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url){
            isStartFocusCounting = true;
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}




