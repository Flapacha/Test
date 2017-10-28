package com.wedev.test.testapp.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedev.test.testapp.Data.DataListener;
import com.wedev.test.testapp.Data.DataLoaderImpl;
import com.wedev.test.testapp.R;
import com.wedev.test.testapp.models.Commentaire;
import com.wedev.test.testapp.models.Post;

import java.util.ArrayList;

public class CommentairesActivity extends AppCompatActivity implements DataListener{

    ArrayList<Commentaire> mCommentaires;
    Post post;

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaires);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        int id = i.getIntExtra("post_id",-1);
        int postId = i.getIntExtra("user_id",-1);
        String title = i.getStringExtra("post_title");
        String body = i.getStringExtra("post_body");

        mRecyclerView = (RecyclerView) findViewById(R.id.commentaireRecyclerView);

        post = new Post(id,postId,title,body);

        ((TextView)findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView)findViewById(R.id.postMessage)).setText(post.getBody());

        DataLoaderImpl.getDataLoader(this).getPostComment(post,this);



    }



    public void initActivity(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(new Adapter(mCommentaires));
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


    /*
    * methode utiliser pour signifier a l'utilisateur que la demande de donnée a echouer et lui offrir l'option de reesayer
    * */
    private void showLoadingFailed() {

        findViewById(R.id.loading_failed).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_failed_reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.loading).setVisibility(View.VISIBLE);
                findViewById(R.id.loading_failed).setVisibility(View.GONE);
                DataLoaderImpl.getDataLoader(CommentairesActivity.this).getPostComment(post,CommentairesActivity.this);
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
            mCommentaires = (ArrayList<Commentaire>) data;
            initActivity();
        }

        else{
            showLoadingFailed();
        }


    }

    private class Holder extends RecyclerView.ViewHolder{

        TextView commentTitle,
        commentBody,
        authorMail;


        public Holder(View itemView) {
            super(itemView);
            commentTitle = (TextView) itemView.findViewById(R.id.commentTitle);
            commentBody = (TextView) itemView.findViewById(R.id.commentBody);
            authorMail = (TextView) itemView.findViewById(R.id.authorMail);
        }

        public void hydrateHolder(Commentaire comment){

            commentTitle.setText(comment.getTitle());
            commentBody.setText(comment.getBody());
            authorMail.setText(comment.getAuthor());

        }

    }

    private class Adapter extends RecyclerView.Adapter{


        ArrayList<Commentaire> comments;

        public Adapter(ArrayList<Commentaire> comments){
            this.comments = comments;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(CommentairesActivity.this).inflate(R.layout.comment_list_item,parent,false);

            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder)holder).hydrateHolder(comments.get(position));
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }

}
