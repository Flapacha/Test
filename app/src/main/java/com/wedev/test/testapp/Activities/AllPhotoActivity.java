package com.wedev.test.testapp.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wedev.test.testapp.Data.DataListener;
import com.wedev.test.testapp.Data.DataLoaderImpl;
import com.wedev.test.testapp.R;
import com.wedev.test.testapp.models.Album;
import com.wedev.test.testapp.models.Photo;

import java.util.ArrayList;


/*
* CETTE ACTIVITE EST L'ECRAN QUI AFFICHE TOUS LES PHOTOS COMPRISENT DANS LA SOURCE.
*
* */

public class AllPhotoActivity extends AppCompatActivity implements DataListener {
    ArrayList<Photo> mPhotos;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_album_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.albumContentListRecyclerView);

        DataLoaderImpl.getDataLoader(this).getAllPhoto(this);
    }

    public void initActivity(){

        Adapter mAdapter = new Adapter(mPhotos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mAdapter);
        hideLoading();

    }

    /*
    * cette methode est utiliser pour cacher le loader lorsque nous avons obtenus des donnés avec succes
    * */

    private void hideLoading() {
        findViewById(R.id.loading).animate()
                .alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                findViewById(R.id.loading).setVisibility(View.GONE);
                findViewById(R.id.activity_content).setScaleX(0.5f);
                findViewById(R.id.activity_content).setScaleY(0.5f);
                findViewById(R.id.activity_content).setAlpha(0f);
                findViewById(R.id.activity_content).animate()
                        .scaleX(1)
                        .scaleY(1)
                        .alpha(1)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                findViewById(R.id.activity_content).setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();



                findViewById(R.id.activity_content).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();


    }


    /*
    * methode utiliser pour signifier a l'utilisateur que la demande de donnée a echouer et lui offrir l'option de reesayer
    * */
    public void showLoadingFail(){
        findViewById(R.id.loading_failed).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_failed_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading).setVisibility(View.VISIBLE);
                findViewById(R.id.loading_failed).setVisibility(View.GONE);
                DataLoaderImpl.getDataLoader(AllPhotoActivity.this).getAllPhoto(AllPhotoActivity.this);
            }
        });
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    /*
    *
    * on verifie dans la methode OnDataResult les resultats de la demande de donnée effectuer a l'implementation de l'interface DataLoaderImpl
    *
    * */

    @Override
    public void onDataResult(boolean success, Object data) {

        if(success){
            mPhotos = (ArrayList<Photo>) data;
            initActivity();
        }

        else{
            showLoadingFail();
        }

    }

    public class Holder extends RecyclerView.ViewHolder{

        public View rootView;
        public ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            rootView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.albumContentThumbnail);
        }

        public View getRootView(){
            return rootView;
        }

        public void bindView(Photo photo){

            Picasso.with(AllPhotoActivity.this)
                    .load(photo.getThumbnailUrl()).into(imageView);

        }

    }

    public class Adapter extends RecyclerView.Adapter{

        ArrayList<Photo> photos;

        public Adapter(ArrayList<Photo> mPhotos) {
            photos = mPhotos;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(AllPhotoActivity.this).inflate(R.layout.photo_item,parent,false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder)holder).bindView(photos.get(position));
            ((Holder)holder).getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent("com.wedev.test.testapp.activities.PhotoGalleryActivity");
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount(){
            return photos.size();
        }
    }

}
