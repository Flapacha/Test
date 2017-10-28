package com.wedev.test.testapp.models;

/**
 * Created by Javenchi on 27/10/2017.
 */

public class Address {

    private String mStreet;
    private String mSuite;
    private String mCity;
    private String mZipcode;
    private GeoPosition mPosition;


    public Address(String street,String suite,String city,String zipeCode,GeoPosition position){

        mStreet = street;
        mSuite = suite;
        mCity = city;
        mZipcode = zipeCode;
        mPosition = position;

    }


    public String getStreet(){
        return mStreet;
    }

    public String getSuite(){
        return mSuite;
    }

    public String getCity(){
        return mCity;
    }

    public String getZipCode(){
        return mZipcode;
    }

    public GeoPosition getPosition(){
        return mPosition;
    }

    public static class GeoPosition{

        long mLongitude;
        long mLatitude;

        public GeoPosition(long longitude,long latitude){

            mLongitude = longitude;
            mLatitude = latitude;

        }

        public long longitude(){
            return mLongitude;
        }

        public long getLatitude(){return mLatitude;}

    }

}
