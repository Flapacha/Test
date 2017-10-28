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
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedev.test.testapp.Data.DataListener;
import com.wedev.test.testapp.Data.DataLoaderImpl;
import com.wedev.test.testapp.R;
import com.wedev.test.testapp.models.Post;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
* Activite charger d'afficher tous les posts contenue dans la source de donnée
* */

public class PostActivity extends AppCompatActivity implements DataListener{

    ArrayList<Post> mPostList;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataLoaderImpl.getDataLoader(this).getAllPost(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.postRecyclerView);

    }

    @Override
    public void onDataResult(boolean success, Object data) {

        if(success){
            mPostList = (ArrayList<Post>) data;
            initActivity();
        }

        else{
            showLoadingFailed();
        }

    }

    private void showLoadingFailed() {
        findViewById(R.id.loading_failed).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_failed_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading).setVisibility(View.VISIBLE);
                findViewById(R.id.loading_failed).setVisibility(View.GONE);
                DataLoaderImpl.getDataLoader(PostActivity.this).getAllPost(PostActivity.this);
            }
        });
        findViewById(R.id.loading).setVisibility(View.GONE);
    }

    private void initActivity() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(new Adapter(mPostList));
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


    private class Holder extends RecyclerView.ViewHolder{

        public View rootView;
        TextView postBody;
        TextView postTitle;
        public Holder(View itemView) {
            super(itemView);
            rootView = itemView;
            postBody= (TextView) itemView.findViewById(R.id.postMessage);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
        }

        public View getRootView(){
            return rootView;
        }

        public void hydrateItemBinding(Post post){

            postBody.setText(post.getBody());
            postTitle.setText(post.getTitle());

        }
    }

    private class Adapter extends RecyclerView.Adapter{

        private ArrayList<Post>  mArray;

        public Adapter(ArrayList<Post> posts){
            mArray = posts;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(PostActivity.this).inflate(R.layout.post_item,parent,false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((Holder)holder).hydrateItemBinding(mArray.get(position));
            ((Holder)holder).getRootView().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Post clickedPost = mArray.get(position);

                    Intent i = new Intent(PostActivity.this,CommentairesActivity.class);
                    i.putExtra("post_id",clickedPost.getId());
                    i.putExtra("post_title",clickedPost.getTitle());
                    i.putExtra("post_body",clickedPost.getBody());

                    PostActivity.this.startActivity(i);
                }


            });
        }

        @Override
        public int getItemCount() {
            return mArray.size();
        }
    }


}
