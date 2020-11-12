package com.example.datastorage;

public class NewsObject
{
    private int id;
    private String newsId;
    private String title;
    private String url;
    
    public NewsObject(int id, String newsId, String title, String url)
    {
        this.id = id;
        this.newsId = newsId;
        this.title = title;
        this.url = url;
    }// end NewsObject()
    
    
    public void SetId (int id)
    {
        this.id = id;
    }
    
    public void SetNewsId (String newsId)
    {
        this.newsId = newsId;
    }
    
    public void SetTitle (String title)
    {
        this.title = title;
    }
    
    public void SetUrl (String url)
    {
        this.url = url;
    }
    
    public int GetId ()
    {
        return id;
    }
    
    public String GetNewsId ()
    {
        return newsId;
    }
    
    public String GetTitle ()
    {
        return title;
    }
    
    public String GetUrl ()
    {
        return url;
    }
    
    @Override
    public String toString()
    {
        return this.GetTitle();
    }
    
    
    

}// end class NewsObject
