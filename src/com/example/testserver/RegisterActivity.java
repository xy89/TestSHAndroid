package com.example.testserver;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RegisterActivity extends Activity {
    
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);     
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("http://10.32.168.175:8080/TestSH/Register.jsp");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;                
            }
            
        }) ;
    }
    
    /*@Override
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();
                return true;
       }
       return super.onKeyDown(keyCode, event);
        
    }*/

}
