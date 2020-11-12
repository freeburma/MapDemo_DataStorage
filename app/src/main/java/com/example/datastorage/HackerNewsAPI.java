package com.example.datastorage;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HackerNewsAPI extends AsyncTask<String, Void, String>
{
    
    @Override
    protected String doInBackground (String... apiUrls)
    {
        String result = "";
        try
        {
            URL url = new URL(apiUrls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            
            int data = reader.read();
            
            while (data != -1)
            {
                result += (char) data;
                
                data = reader.read();
            
            }// end while
    
    
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    
        return result;
    
    }// end doInBackground()
    
//    @Override
//    protected void onPostExecute (String s)
//    {
//
//        try
//        {
//            JSONObject jsonObject = new JSONObject(s);
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//
//        super.onPostExecute(s);
//    }
    
}// end class HackerNewsAPI
