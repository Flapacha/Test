package com.wedev.test.testapp.Data;

import android.content.Context;
import android.util.Log;

import com.wedev.test.testapp.models.Album;
import com.wedev.test.testapp.models.Commentaire;
import com.wedev.test.testapp.models.Photo;
import com.wedev.test.testapp.models.Post;
import com.wedev.test.testapp.models.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Javenchi on 27/10/2017.
 *
 * Implementation de l'inteface DataLoader ,
 * elle utilise la classe internetDataDownload pour telecharger les donné sur le net
 * et utilise le ModelExtractorFromJSON pour parsé ces données crées.
 *
 */

public class DataLoaderImpl implements DataLoader {

    public static final String ALL_USER_URL ="http://jsonplaceholder.typicode.com/users";
    public static final String ALL_ALBUM_URL ="http://jsonplaceholder.typicode.com/albums";
    public static final String ALL_PHOTO_URL ="http://jsonplaceholder.typicode.com/photos";
    public static final String ALL_POST_URL = "http://jsonplaceholder.typicode.com/posts";
    public static final String ALL_COMMENT_URL ="http://jsonplaceholder.typicode.com/comments";



    public static final String POST_COMMENT_URL = "http://jsonplaceholder.typicode.com/posts/{{id}}/comments";
    public static final String ALBUM_PHOTO_URL = "http://jsonplaceholder.typicode.com/photos?albumId={{id}}";

    private static DataLoaderImpl DataLoader;



    private Context mContext;

    private DataLoaderImpl(Context c){
        mContext = c;
    }

    @Override
    public void getUtilisateurList(final DataListener listener) {

        InternetDataDownloader.getInstance(mContext)
                .downloadData(getUrl(ALL_USER_URL),

                        new InternetDataDownloader.JSONArrayDownloadListener() {
                            @Override
                            public void onDownloadFinished(JSONArray result) {
                                ArrayList<Utilisateur> allUser = new ArrayList<>();
                                allUser = ModelExtractorFromJSON.getUtilisateurList(result);
                                listener.onDataResult(true,allUser);
                            }

                            @Override
                    public void onDownloadFinished(String result) {

                    }

                    @Override
                    public void onDownloadProgress(double progress) {

                    }

                    @Override
                    public void onDownloadError(String errorMessage) {
                        listener.onDataResult(false,null);
                    }
                });

    }

    @Override
    public void getAllAlbumList(final DataListener listener) {

        InternetDataDownloader.getInstance(mContext)
                .downloadData(

                        getUrl(
                                ALL_ALBUM_URL
                        ),

                        new InternetDataDownloader.JSONArrayDownloadListener() {
                            @Override
                            public void onDownloadFinished(JSONArray result) {

                                ArrayList<Album> albums = ModelExtractorFromJSON.getAlbum(result);

                                listener.onDataResult(true,albums);
                            }

                            @Override
                            public void onDownloadFinished(String result) {

                            }

                            @Override
                            public void onDownloadProgress(double progrss) {

                            }

                            @Override
                            public void onDownloadError(String errorMessage) {
                                listener.onDataResult(false,null);

                            }
                        }

                );

    }

    @Override
    public void getAllPhoto(final DataListener listener) {

        InternetDataDownloader.getInstance(mContext)
                .downloadData(getUrl(ALL_PHOTO_URL),
                        new InternetDataDownloader.JSONArrayDownloadListener() {
                            @Override
                            public void onDownloadFinished(JSONArray result) {

                                ArrayList<Photo> albumArrayList = ModelExtractorFromJSON.getPhoto(result);

                                listener.onDataResult(true,albumArrayList);
                            }

                            @Override
                            public void onDownloadFinished(String result) {
                                listener.onDataResult(true,result);
                            }

                            @Override
                            public void onDownloadProgress(double progress) {

                            }

                            @Override
                            public void onDownloadError(String errorMessage) {
                                listener.onDataResult(false,null);
                            }
                        });
    }


    public void getAllPost(final DataListener listener){

        InternetDataDownloader.getInstance(mContext)
                .downloadData(

                        getUrl(
                                ALL_POST_URL
                        ),

                        new InternetDataDownloader.JSONArrayDownloadListener() {

                            @Override
                            public void onDownloadFinished(JSONArray result) {

                                ArrayList<Post> postList = ModelExtractorFromJSON.getPost(result);
                                listener.onDataResult(true,postList);

                            }

                            @Override
                            public void onDownloadFinished(String result) {

                            }

                            @Override
                            public void onDownloadProgress(double progrss) {

                            }

                            @Override
                            public void onDownloadError(String errorMessage) {
                                listener.onDataResult(false,null);

                            }
                        }
                );

    }

    public void getAllCommentaire(final DataListener listener){

        InternetDataDownloader.getInstance(mContext)
                .downloadData(

                        getUrl(ALL_COMMENT_URL),
                        new InternetDataDownloader.JSONArrayDownloadListener() {
                            @Override
                            public void onDownloadFinished(JSONArray result) {

                                ArrayList<Commentaire> commentaires = ModelExtractorFromJSON.getCommentaires(result);
                                listener.onDataResult(true,result);
                            }

                            @Override
                            public void onDownloadFinished(String result) {

                            }

                            @Override
                            public void onDownloadProgress(double progrss) {

                            }

                            @Override
                            public void onDownloadError(String errorMessage) {

                            }
                        }

                );

    }




    @Override
    public  void  getAlbumPhoto(final Album album, final DataListener listener) {

        InternetDataDownloader.getInstance(mContext)
                .downloadData(getUrl(ALBUM_PHOTO_URL.replace("{{id}}",""+album.getAlbumId())), new InternetDataDownloader.JSONArrayDownloadListener() {
                    @Override
                    public void onDownloadFinished(JSONArray result) {

                        ArrayList<Photo> photos = ModelExtractorFromJSON.getPhoto(result);
                        listener.onDataResult(true,photos);

                    }

                    @Override
                    public void onDownloadFinished(String result) {


                    }

                    @Override
                    public void onDownloadProgress(double progress) {

                    }

                    @Override
                    public void onDownloadError(String errorMessage) {
                        listener.onDataResult(false,null);
                    }
                });

    }

    @Override
    public void getPostComment(Post post, final DataListener listener) {

        InternetDataDownloader.getInstance(mContext)
                .downloadData(getUrl(POST_COMMENT_URL.replace("{{id}}",""+post.getId())),
                        new InternetDataDownloader.JSONArrayDownloadListener() {
                    @Override
                    public void onDownloadFinished(JSONArray array) {

                        ArrayList<Commentaire> commentaires = ModelExtractorFromJSON.getCommentaires(array);

                        listener.onDataResult(true,commentaires);
                    }

                            @Override
                            public void onDownloadFinished(String result) {

                            }

                            @Override
                    public void onDownloadProgress(double progress) {

                    }

                    @Override
                    public void onDownloadError(String errorMessage) {
                        listener.onDataResult(false,null);
                    }
                });

    }


    public static DataLoaderImpl getDataLoader(Context c){
        if(DataLoader==null) {
            DataLoader = new DataLoaderImpl(c);
        }
        return DataLoader;
    }

    private URL getUrl(String s){

        try{
            URL url = new URL(s);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
