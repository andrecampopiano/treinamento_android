package com.example.camara.myapplication.model.persistence;

import android.database.Cursor;

import com.example.camara.myapplication.model.entities.User;

import java.util.List;

/**
 * Created by andre on 30/07/15.
 */
public interface UserRepository {
    public abstract Boolean findUser(User user);
}
