package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Utilisateur {

    private int mId;
    private String mName;
    private String mUserName;
    private String mEmail;
    private Address mAddress;
    private String mPhone;
    private String mWebsite;
    private Company mCompany;


    public Utilisateur(int id,String name,String username,String email,String phone,String website,Address address,Company company){

        mId = id;
        mName = name;
        mUserName = name;
        mEmail = email;
        mPhone = phone;
        mWebsite = website;
        mAddress = address;
        mCompany = company;

    }

    public int getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getUsername(){
        return mUserName;
    }

    public String getEmail(){return mEmail;}

    public String getPhone(){return mPhone;}

    public String getWebSite() {return mWebsite;}

    public Address getAddress(){ return mAddress; }

    public Company getCompany(){ return mCompany; }




}
