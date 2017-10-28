package com.wedev.test.testapp.Data;

import android.util.Log;

import com.wedev.test.testapp.models.Address;
import com.wedev.test.testapp.models.Album;
import com.wedev.test.testapp.models.Commentaire;
import com.wedev.test.testapp.models.Company;
import com.wedev.test.testapp.models.Photo;
import com.wedev.test.testapp.models.Post;
import com.wedev.test.testapp.models.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Javenchi on 27/10/2017.
 *
 * Cette classe est utilisé par le l'implementation de l'interface DataListener
 * Elle prend des JSONObject ou Array en entré et sort un objet Model du projet
 * Son fonctionnement est , tres simple.
 * Pour chaque objet JSONObject elle extrait ses donné pour les utilisé dans la création d'un objet du model cible.
 *
 *
 */

class ModelExtractorFromJSON {

    public static Utilisateur getUtilisateur(@javax.annotation.Nonnull JSONObject object) throws ExtractorError{
        try {
            int utilisateurId = object.getInt("id");
            String name = object.getString("name");
            String userName = object.getString("username");
            String email = object.getString("email");
            String phone = object.getString("phone");
            String website = object.getString("website");

            Company company = getCompany(object.getJSONObject("company"));
            Address address = getAddress(object.getJSONObject("address"));

            Utilisateur utilisateur = new Utilisateur(utilisateurId, name, userName, email,phone,website, address,company);

            return utilisateur;
        }

        catch (JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Une erreur est survenue lors de l'extraction de l'utilisateur du jsonObject");
        }
    }

    public static Album getAlbum(@javax.annotation.Nonnull JSONObject object) throws ExtractorError{

        try{

            int albumId = object.getInt("id");
            int userId = object.getInt("userId");
            String title = object.getString("title");

            return new Album(albumId,userId,title);

        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Erreur lors de l'extraction d'un album du JSON");
        }

    }

    public static Commentaire getCommentaire(@javax.annotation.Nonnull JSONObject object)  throws ExtractorError{

        try {
            int id = object.getInt("id");
            int postId = object.getInt("postId");
            String name = object.getString("name");
            String email = object.getString("email");
            String body = object.getString("body");

            return new Commentaire(id,postId,name,body,email);
        }catch (Exception e){
            e.printStackTrace();

            throw new ExtractorError("Erreur lors de l'extraction du commentaire");
        }



    }

    public static Company getCompany(@javax.annotation.Nonnull JSONObject object)  throws ExtractorError{
        try {

            String companyName = object.getString("name");
            String catchPhrase = object.getString("catchPhrase");
            String bs = object.getString("bs");

            return new Company(companyName,catchPhrase,bs);

        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de l'extraction de compagny");
        }
    }

    public static Photo getPhoto(@javax.annotation.Nonnull JSONObject object)  throws ExtractorError{

        try {

            int id = object.getInt("id");
            int albumId = object.getInt("albumId");
            String title = object.getString("title");
            String url = object.getString("url");
            String urlThumbnail = object.getString("thumbnailUrl");

            Photo photo = new Photo(id,albumId,title,url,urlThumbnail);
            return photo;
    } catch (JSONException e) {
        e.printStackTrace();
    }
        return null;
    }

    public static Post getPost(@javax.annotation.Nonnull JSONObject object)  throws ExtractorError{

        try {
            int id = object.getInt("id");
            int userId = object.getInt("userId");
            String title = object.getString("title");
            String body = object.getString("body");

            return new Post(id, userId, title, body);

        }catch (JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de l'extraction des posts");
        }
    }

    public static Address getAddress(@javax.annotation.Nonnull JSONObject object)  throws ExtractorError{

        try {
            String street = object.getString("street");
            String suite = object.getString("suite");
            String city = object.getString("city");
            String zipCode = object.getString("zipcode");
            Address.GeoPosition position = getGeoPosition(object.getJSONObject("geo"));

            return  new Address(street, suite, city, zipCode, position);
        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Erreur lors de l'extraction de l'addresse");
        }
    }

    public static Address.GeoPosition getGeoPosition(JSONObject jsonObject) throws  ExtractorError{

        try {
            long longitude = jsonObject.getLong("lng");
            long latitude = jsonObject.getLong("lat");


            return new Address.GeoPosition(longitude,latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Utilisateur> getUtilisateurList(JSONArray array)throws ExtractorError{

        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();

        try {
            for (int i = 0; i<array.length(); i++) {
                utilisateurs.add(getUtilisateur(array.getJSONObject(i)));
            }
            return utilisateurs;
        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de la reconstitution de l'array");
        }

    }

    public static ArrayList<Post> getPost(@javax.annotation.Nonnull JSONArray array)  throws ExtractorError{

        ArrayList<Post> posts = new ArrayList<>();

        try {
            for (int i = 0; i<array.length(); i++) {
                posts.add(getPost(array.getJSONObject(i)));
            }
            return posts;
        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de la reconstitution de l'array");
        }

    }

    public static ArrayList<Photo> getPhoto(@javax.annotation.Nonnull JSONArray array)  throws ExtractorError{

        ArrayList<Photo> photos = new ArrayList<>();

        try {
            for (int i = 0; i<array.length(); i++) {
                photos.add(getPhoto(array.getJSONObject(i)));
            }
            return photos;
        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de la reconstitution de l'array");
        }

    }

    public static ArrayList<Album> getAlbum(JSONArray array) throws ExtractorError{

        ArrayList<Album> albumList = new ArrayList<>();

        try {
            for (int i = 0; i<array.length(); i++) {
                albumList.add(getAlbum(array.getJSONObject(i)));
            }
            return albumList;
        }catch(JSONException e){
            e.printStackTrace();
            throw new ExtractorError("Error lors de la reconstitution de l'array");
        }

    }

    public static ArrayList<Commentaire> getCommentaires(JSONArray array) {

        ArrayList<Commentaire> list = new ArrayList<>();

        for(int i=0;i<array.length();i++){

            try {
                list.add(getCommentaire(array.getJSONObject(i)));
            } catch (JSONException e) {
              throw new ExtractorError("Error while extract commentaire");
            }

        }

        return list;
    }


    public static class ExtractorError extends IllegalArgumentException{

        public ExtractorError(String exception){

        }

    }

}
