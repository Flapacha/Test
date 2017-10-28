package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Album {

    private int mId;
    private int mUserId;
    private String mTitle;

    public Album(int id,int userId,String title){
        mId = id;
        mUserId = userId;
        mTitle = title;
    }

    public int getAlbumId(){
        return mId;
    }

    public int getUserId(){
        return mUserId;
    }

    public String getTitle(){
        return mTitle;
    }

}
