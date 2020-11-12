package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailWebView extends AppCompatActivity
{
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_web_view);
    
        Intent intent = this.getIntent();
        int id = intent.getIntExtra("newsID", 0);
    
        WebView webView = (WebView) findViewById(R.id.newsDetailWebviewId);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    
        /// Creating SQLite Db
        String dbName = "HackerNewsDb";
        String tableName = "News";
        String url = "";
    
        SQLiteDatabase newsDb = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    
        /// Creating a News Table
        String getNewsDataQuery = "SELECT url FROM " + tableName + " WHERE id = " + id;
    
        Cursor newsData = newsDb.rawQuery(getNewsDataQuery, null);
        int idIndex = newsData.getColumnIndex("id");
        int newsIdIndex = newsData.getColumnIndex("NewsId");
        int titleIndex = newsData.getColumnIndex("title");
        int urlIndex = newsData.getColumnIndex("url");
    
        if (newsData != null)
        {
            newsData.moveToFirst();
            do
            {
                url = newsData.getString(urlIndex);
            
            }
            while (newsData.moveToNext());
        }
        
        webView.loadUrl(url);
        
        
    }// end onCreate()
    
}