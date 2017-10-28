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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedev.test.testapp.Data.DataListener;
import com.wedev.test.testapp.Data.DataLoader;
import com.wedev.test.testapp.Data.DataLoaderImpl;
import com.wedev.test.testapp.R;
import com.wedev.test.testapp.models.Album;

import java.util.ArrayList;


/*
* Activite charger d'afficher tous les albums contenue dans la source de donnée
* Lors du clique sur un album , elle affiche une nouvelle activité qui comprend la liste de tous les medias contenue dans cet album
* */


public class AlbumListActivty extends AppCompatActivity implements DataListener{

    ArrayList<Album> mAlbums;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.albumListRecyclerView);
        DataLoaderImpl.getDataLoader(this).getAllAlbumList(this);

    }


    private void initActivity(){


        Adapter adapter = new Adapter(mAlbums);
        mRecyclerView.setLayoutManager(new GridLayoutManager(AlbumListActivty.this,2));
        mRecyclerView.setAdapter(adapter);
        removeLoader();

    }

    /*
    * cette methode est utiliser pour cacher le loader lorsque nous avons obtenus des donnés avec succes
    * */

    private void removeLoader(){
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
    private void loadingFailed(){
        findViewById(R.id.loading_failed).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_failed_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading).setVisibility(View.VISIBLE);
                findViewById(R.id.loading_failed).setVisibility(View.GONE);
                DataLoaderImpl.getDataLoader(AlbumListActivty.this).getAllAlbumList(AlbumListActivty.this);
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

        if(success) {

            mAlbums = (ArrayList<Album>) data;
            initActivity();

        }

        else{
            loadingFailed();
        }

    }


    private class Holder extends RecyclerView.ViewHolder {

        private View rootView;

        TextView albumName;

        public Holder(View itemView) {
            super(itemView);
            rootView = itemView;
            albumName = (TextView) rootView.findViewById(R.id.albumName);
        }

        public void hydrateOnBind(Album album){
            albumName.setText(album.getTitle());
        }

        public View getRootView(){
            return rootView;
        }


    }

    private class Adapter extends RecyclerView.Adapter{

        ArrayList<Album> mArrayList;

        public Adapter(ArrayList<Album> album){
            mArrayList = album;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(AlbumListActivty.this).inflate(R.layout.album_item_layout,parent,false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
            ((Holder)holder).hydrateOnBind(mArrayList.get(position));
            ((Holder)holder).getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = mArrayList.get(position);
                    Intent intent = new Intent(AlbumListActivty.this,AlbumContentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("album_id",album.getAlbumId());
                    bundle.putInt("user_id",album.getUserId());
                    bundle.putString("album_name",album.getTitle());

                    intent.putExtra("extra",bundle);

                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }
    }


}
