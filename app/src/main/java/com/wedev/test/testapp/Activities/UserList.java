package com.wedev.test.testapp.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedev.test.mapsmodule.Maps;
import com.wedev.test.testapp.Data.DataListener;
import com.wedev.test.testapp.Data.DataLoaderImpl;
import com.wedev.test.testapp.R;
import com.wedev.test.testapp.models.Utilisateur;

import java.util.ArrayList;

/*

* Activite charger d'afficher tous les utlilisateur dans la source de donnés
* Lors d'un clique sur une utilisateur present dans la liste , elle utilise le module passe en demmarrant une de ses activités.
*
* */

public class UserList extends AppCompatActivity implements DataListener {

    ArrayList<Utilisateur> userList;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.userListRecyclerView);

        DataLoaderImpl.getDataLoader(this).getUtilisateurList(this);
    }



    public void initActivity(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(new Adapter(userList));
        hideLoading();

    }

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

    public void showLoadingFailed(){

        findViewById(R.id.loading_failed).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_failed_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading).setVisibility(View.VISIBLE);
                findViewById(R.id.loading_failed).setVisibility(View.GONE);
                DataLoaderImpl.getDataLoader(UserList.this).getUtilisateurList(UserList.this);
            }
        });
        findViewById(R.id.loading).setVisibility(View.GONE);

    }


    @Override
    public void onDataResult(boolean success, Object data) {

        if(success){
            userList = (ArrayList<Utilisateur>) data;
            Log.e("TELECHARGEMENT  ","TELECHARGEMENT TERMINER=>"+userList.size());
            initActivity();
        }

        else{
            showLoadingFailed();
        }

    }

    private class Holder extends RecyclerView.ViewHolder{

        private View rootView;


        private TextView userNameAndSurname;
        private TextView userMail;
        private TextView userCity;
        private TextView userNumber;


        public Holder(View itemView) {
            super(itemView);
            rootView = itemView;

            userNameAndSurname = (TextView) rootView.findViewById(R.id.userNameAndSurname);
            userMail = (TextView) rootView.findViewById(R.id.userMail);
            userCity = (TextView) rootView.findViewById(R.id.userCity);
            userNumber = (TextView) rootView.findViewById(R.id.userNumber);

        }

        public View getRootView(){
            return rootView;
        }

        public void bindHolder(Utilisateur user){

            userNameAndSurname.setText(user.getName());
            userMail.setText(user.getEmail());
            userCity.setText(user.getAddress().getCity());
            userNumber.setText(user.getPhone());
        }

    }

    private class Adapter extends RecyclerView.Adapter{

        private ArrayList<Utilisateur> mArray;

        public Adapter(ArrayList<Utilisateur> user){
            mArray = user;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(UserList.this).inflate(R.layout.user_item,parent,false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((Holder)holder).bindHolder(mArray.get(position));
            ((Holder)holder).getRootView().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(UserList.this,Maps.class);
                            intent.putExtra("longitude",mArray.get(position).getAddress().getPosition().longitude());
                            intent.putExtra("latitude",mArray.get(position).getAddress().getPosition().getLatitude());

                            startActivity(intent);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return mArray.size();
        }
    }

}
