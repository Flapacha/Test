package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Commentaire {

    private int mId;
    private int mPostId;
    private String mCommentTitle;
    private String mCommentPost;
    private String mEmail;

    public Commentaire(int id,int postId,String commentTitle,String commentPost,String email){

        mId = id;
        mPostId = postId;
        mCommentTitle = commentTitle;
        mCommentPost = commentPost;
        mEmail = email;

    }


    public String getTitle() {
        return mCommentTitle;
    }

    public String getBody() {
        return mCommentPost;
    }

    public String getAuthor() {
        return mEmail;
    }
}
