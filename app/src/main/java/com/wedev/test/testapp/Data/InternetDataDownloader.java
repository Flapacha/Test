package com.wedev.test.testapp.Data;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.Pair;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Javenchi on 27/10/2017.
 *
 * InternetDataDownloader
 *
 * Cette classe est utilisé par l'implementation du DataLoader.
 * Elle sert en quelque sorte comme source de donnée , car c'est elle qui est chargé de produire toute source de donnée que ce soit.
 *
 * Il utilise un thread handler()Download qui agit comme un Worker.C'est ce thread handler qui initie la connection avec la source de donnée.
 * Elle lock l'ui Thread lors de sa création pour evité les races condition lors d'une demande de Telechargement.
 * Le lock se termine lorsque le Worker s'est initialisé correctement.
 *
 *
 *
 */

public class InternetDataDownloader {

    private static InternetDataDownloader mDownloader;

    public static final int TYPE_JSON_OBJECT=0;
    public static final int TYPE_JSON_ARRAY=1;
    public static final int TYPE_TEXT=2;

    private Object initialisationLocker =  new Object();

    private Context mContext;
    private DownloaderThreadHandler downloadThread;


    private InternetDataDownloader(Context c){
        mContext = c;

            downloadThread = new DownloaderThreadHandler("Downloader");
            downloadThread.start();

        try {

            /*
            * NOUS LOCKONS
            * */

            synchronized (initialisationLocker) {
                initialisationLocker.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /*
    * cette methode sert a telecharger les données situés a l'adresse passé en parametre.
    * l'objet listener reçoit les differents évênement de l'avancer du telechargement
    * */
    public void downloadData(URL url , DownloadListener listener){

        downloadThread.postDataDownload(url,listener,TYPE_TEXT);

    }

    public void downloadData(URL url , JSONObjectDownloadListener listener){

        if(downloadThread==null) {
            downloadThread = new DownloaderThreadHandler("Downloader");
            downloadThread.start();
        }

        downloadThread.postDataDownload(url,listener,TYPE_JSON_OBJECT);

    }

    public void downloadData(URL url , JSONArrayDownloadListener listener){

        if(downloadThread==null) {
            downloadThread = new DownloaderThreadHandler("Downloader");
            downloadThread.start();
        }

        downloadThread.postDataDownload(url,listener,TYPE_JSON_ARRAY);

    }

    public static InternetDataDownloader getInstance(Context c){
        if(mDownloader==null) {
            mDownloader = new InternetDataDownloader(c);

        }
        return mDownloader;
    }


    /*
    * Interface de base servant de port d'écoute de l'avancer d'un telechargement.
    * Le resultat d'un telechargement effectué avec cette interface sera livrer sous forme d'object JSON en cas de succes ou sera donné sous forme null en cas d'echec de Parsing
    * */

    public static interface DownloadListener{
        public void onDownloadFinished(String result);
        public void onDownloadProgress(double progrss);
        public void onDownloadError(String errorMessage);
    }


    /*
    * Interface servant de port d'écoute de l'avancer d'un telechargement.
    * Le resultat d'un telechargement effectué avec cette interface sera livrer sous forme d'object JSON en cas de succes ou sera donné sous forme null en cas d'echec de Parsing
    * */
    public static interface JSONObjectDownloadListener extends DownloadListener{
        public void onDownloadFinished(JSONObject result);
    }


    /*
    * Interface de base servant de port d'écoute de l'avancer d'un telechargement.
    * Le resultat d'un telechargement effectué avec cette interface sera livrer sous forme d'object JSON en cas de succes ou sera donné sous forme null en cas d'echec de Parsing
    * */
    public static interface  JSONArrayDownloadListener extends DownloadListener{
        public void onDownloadFinished(JSONArray result);
    }


    private class DownloaderThreadHandler extends HandlerThread {


        private Handler mHandler;
        private Handler mUiHandler ;


        public DownloaderThreadHandler(String name) {
            super(name);
            mUiHandler = new Handler(Looper.getMainLooper());
        }

        public DownloaderThreadHandler(String name, int priority) {
            super(name, priority);
        }

        @Override
        public void onLooperPrepared(){
            mHandler = new Handler() {



                @Override
                public void handleMessage(Message m){

                   HashMap<String,Object> map = (HashMap<String, Object>) m.obj;

                    URL url = (URL) map.get("URL");
                    final DownloadListener listener = (DownloadListener) map.get("LISTENER");
                    final int type = (int) map.get("TYPE");

                    try {

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        final double contentLength = connection.getContentLength();

                        StringBuilder builder = new StringBuilder();

                        InputStream stream = connection.getInputStream();
                        BufferedReader bis = new BufferedReader(new InputStreamReader(stream));

                        String line ="";
                        int lineReaded=0;
                        while((line = bis.readLine())!=null){
                            lineReaded+=line.length();
                            final int tempLineReaded = lineReaded;
                            builder.append(line);
                            mUiHandler.post(new Runnable() {


                                @Override
                                public void run() {
                                    listener.onDownloadProgress(((double)tempLineReaded)/contentLength);
                                }
                            });
                        }


                        final String data = builder.toString();
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(type==TYPE_JSON_ARRAY){

                                    try {
                                        JSONArray array = new JSONArray(data);
                                        ((JSONArrayDownloadListener)listener).onDownloadFinished(array);
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                        listener.onDownloadError("Cannot parse data to JSON ARRAY");
                                    }
                                }

                                else if(type==TYPE_JSON_OBJECT){

                                    try {
                                        JSONObject object = new JSONObject(data);
                                        ((JSONObjectDownloadListener)listener).onDownloadFinished(object);
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                        listener.onDownloadError("Cannot parse data to JSON OBJECT");
                                    }
                                }

                                else if(type==TYPE_TEXT){
                                    listener.onDownloadFinished(data);
                                }

                            }

                        });

                    }catch (IOException e){
                        e.printStackTrace();
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloadError("Erreur lors du telechargement d'informations");
                            }
                        });
                    }

                }

            };


            /*
            * NOUS LIBERONS L'UI THREAD DANS LE THREAD DU WORKER ICI.
            * */
            synchronized (initialisationLocker) {
                initialisationLocker.notifyAll();
            }
        }

        public void postDataDownload(URL downloadURL,DownloadListener listener,int downloadType){

            synchronized (initialisationLocker) {

                Message m = mHandler.obtainMessage();
                HashMap<String, Object> map = new HashMap<>();

                map.put("URL", downloadURL);
                map.put("LISTENER", listener);
                map.put("TYPE", downloadType);

                Pair<URL, DownloadListener> p = new Pair<>(downloadURL, listener);

                m.obj = map;

                m.sendToTarget();
            }

        }

    }

}
