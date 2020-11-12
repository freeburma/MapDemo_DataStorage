package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class NewsApp extends AppCompatActivity
{
    private String TAG = "";
    static ArrayList<String> newsList;
    static ArrayList<NewsObject> newsObjectList; // Ref: https://stackoverflow.com/questions/40232665/add-arraylistobject-to-listview-android
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app);
        
        /// 1. Create Background Task
        ///     => Add URL              @@ DONE
        ///     => Retrieve Data        @@ DONE
        ///     => Store Data in SQLite
        ///     => Display on ListView
        String result = "";
        HackerNewsAPI hackerNewsAPI = new HackerNewsAPI();
        String apiURL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    
    
        ListView newsListView = (ListView) findViewById(R.id.newsListViewId);
    
    
        newsList = new ArrayList<String>();
        newsObjectList = new ArrayList<NewsObject>();
        
        String dbName = "HackerNewsDb";
        String tableName = "News";
        
        try
        {
            result = hackerNewsAPI.execute(apiURL).get();
            
            /// Creating SQLite Db
            SQLiteDatabase newsDb = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    
            /// Get Add data from DB
            String getNewsQuery = "SELECT * FROM " + tableName;
    
            /// Create Cursor
            Cursor newsCursor = newsDb.rawQuery(getNewsQuery, null);
            int rowCount = newsCursor.getCount();
            
            if (rowCount == 0)
            {
    
                /// Creating a News Table
                String dropTable = "DROP TABLE IF EXISTS " + tableName;
                newsDb.execSQL(dropTable);
    
                String newsTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, NewsId VARCHAR, title VARCHAR, url VARCHAR)";
                newsDb.execSQL(newsTableQuery);
    
                /// Adding data to sqlite
                //            String addNewsIDQuery = "INSERT INTO " + tableName + " (id, NewsId) VALUES (1, 'Hello' )";
                //            newsDb.execSQL(addNewsIDQuery);
    
                /// =============== Working Code ======================
                JSONArray jsonArray = new JSONArray(result);
    
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
            
            
                        int key = jsonArray.getInt(i);
                        String keyString = jsonArray.getString(i);
            
                        //                    TAG = "JsonKey";
                        //                    Log.i(TAG, keyString);
            
                        String apiDetailURL = "https://hacker-news.firebaseio.com/v0/item/" + keyString + ".json";
                        HackerNewsAPI getDetailHackerNewsAPi = new HackerNewsAPI();
                        String resultDetail = getDetailHackerNewsAPi.execute(apiDetailURL).get();
                        JSONObject detailNews = new JSONObject(resultDetail);
                        String title = detailNews.getString("title");
                        String url = detailNews.getString("url");
                        try
                        {
                            String addNewsIDQuery = "INSERT INTO " + tableName + " (id, NewsId, title, url) VALUES (" + key + ", '" + keyString + "', '" + title + "', '" + url + "')";
                            newsDb.execSQL(addNewsIDQuery);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
        
                }// end for
            }// end if -> rowCount

            
            /// Get Add data from DB
//            String getNewsQuery = "SELECT * FROM " + tableName;
            
            /// Create Cursor
            newsCursor = newsDb.rawQuery(getNewsQuery, null);
            
            int idIndex = newsCursor.getColumnIndex("id");
            int newsIdIndex = newsCursor.getColumnIndex("NewsId");
            int titleIndex = newsCursor.getColumnIndex("title");
            int urlIndex = newsCursor.getColumnIndex("url");
            
            if (newsCursor != null)
            {
                newsCursor.moveToFirst();
                
                do
                {
                    int id = newsCursor.getInt(idIndex);
                    String newsID = newsCursor.getString(newsIdIndex);
                    String title = newsCursor.getString(titleIndex);
                    String url = newsCursor.getString(urlIndex);
                    
                    newsList.add(title);
                    NewsObject newsObject = new NewsObject(id, newsID, title, url);
                    newsObjectList.add(newsObject);
                    
                }
                while (newsCursor.moveToNext()); // end while
                
            }// end if
            
            
    
    
//            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newsList);
//            newsListView.setAdapter(arrayAdapter);
    
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newsObjectList);
            newsListView.setAdapter(arrayAdapter);
    
            TAG = "";
            Log.i(TAG, "onCreate: " + result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
    
                Intent goToWebView = new Intent(NewsApp.this, NewsDetailWebView.class);
                NewsObject newsObject = newsObjectList.get(position);
    
//                Toast.makeText(NewsApp.this, "Id Par : " + id + ", NewsObj : " + newsObject.GetId(), Toast.LENGTH_LONG).show();
                
                goToWebView.putExtra("newsID", newsObject.GetId());
                startActivity(goToWebView);
            }// end onItemClick()
            
        });
        
        
        /// 2. Add on click activity from selected list item
        ///      => PassIndex and Go to Webview page
        ///      => Display the news
        ///      =>
        ///      =>
        ///      =>
        
        
    }// end onCreate()
    
}