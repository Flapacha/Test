package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Photo {

    private int mId;
    private int mAlbumId;
    private String mTitle;
    private String mUrl;
    private String mThumbnailURL;

    public Photo(int albumId,int id,String title,String url,String thumbnailURL){

        mId = id;
        mAlbumId =albumId;
        mTitle = title;
        mUrl = url;
        mThumbnailURL = thumbnailURL;

    }


    public String getTitle(){
        return mTitle;
    }

    public String getImageURL(){
        return mUrl;
    }

    public String getThumbnailUrl(){
        return mThumbnailURL;
    }

    public int getId(){
        return mId;
    }

    public int getAlbumId(){
        return mAlbumId;
    }

}
