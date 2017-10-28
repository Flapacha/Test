package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Post {

    private int mUserId;
    private int mId;
    private String mTitle;
    private String mBody;

    public Post(int id,int userId,String title,String body){

        mId = id;
        mUserId = userId;
        mTitle = title;
        mBody = body;

    }

    public int getId(){
        return mId;
    }

    public int getUserId(){
        return mUserId;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getBody(){
        return mBody;
    }


}
