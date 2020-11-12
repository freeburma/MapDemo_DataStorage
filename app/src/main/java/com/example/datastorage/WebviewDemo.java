package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewDemo extends AppCompatActivity
{
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_demo);
    
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);   // Enabling JS
        webView.setWebViewClient(new WebViewClient());      // opening website inside the webview

        // webView.loadUrl("http://www.google.com");
        // webView.loadUrl("http://www.grillandgreen.co.nz");
        
        String htmlData = "<html>"
                          + "<body>"
                              + "<h1>"
                                + "Hello World"
                              + "</h1>"
                              + "<p>"
                                + "This is body"
                              + "</p>"
                          + "</body>"
                        + "</html>";
        webView.loadData(htmlData, "text/html", "UTF-8");
        
        
        
        
    }// end onCreate()
    
}