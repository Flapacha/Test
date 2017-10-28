package com.wedev.test.testapp.Data;

import com.wedev.test.testapp.models.Album;
import com.wedev.test.testapp.models.Commentaire;
import com.wedev.test.testapp.models.Photo;
import com.wedev.test.testapp.models.Post;
import com.wedev.test.testapp.models.Utilisateur;

import java.util.ArrayList;

/**
 * Created by Javenchi on 27/10/2017.
 */

public interface DataLoader {

    public void getUtilisateurList(DataListener listener);

    public void getAllAlbumList(DataListener listener);

    public void getAllPhoto(DataListener listener);

    public void getAlbumPhoto(Album album,DataListener listener);

    public void getPostComment(Post post,DataListener listener);

}
