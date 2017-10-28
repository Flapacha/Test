package com.wedev.test.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wedev.test.testapp.Activities.AlbumListActivty;
import com.wedev.test.testapp.Activities.AllPhotoActivity;
import com.wedev.test.testapp.Activities.PostActivity;
import com.wedev.test.testapp.Activities.UserList;

/*
* Interface d'acceuil de l'application utilisé pour mené aux differents autres activittés par le biais des vue presentes.
* */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.openAllUsers).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeActivity.this,UserList.class);
                        startActivity(i);
                    }
                }

        );

        findViewById(R.id.openAllPost).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeActivity.this,PostActivity.class);
                        startActivity(i);
                    }
                }

        );

        findViewById(R.id.openAllAlbums).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeActivity.this,AlbumListActivty.class);
                        startActivity(i);
                    }
                }

        );

        findViewById(R.id.openAllPhoto).setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeActivity.this,AllPhotoActivity.class);
                        startActivity(i);
                    }
                }

        );

    }

}
