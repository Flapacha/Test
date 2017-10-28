package com.wedev.test.testapp.Data;

/**
 * Created by Javenchi on 27/10/2017.
 */

/*
* Interface Utiliser par l'interface DataLoader comme un port de reception d'evênement et de donné a la suite d'une demande de service
* a un objet implementant cette interface.
* */

public interface DataListener{
    void onDataResult(boolean success,Object data);
}