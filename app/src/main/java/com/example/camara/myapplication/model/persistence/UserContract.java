package com.example.camara.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.camara.myapplication.model.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 30/07/15.
 */
public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";

    public static final String[] COLUMNS = {ID, USUARIO, PASSWORD };

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USUARIO + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static String insertUserAdmin(){
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(USUARIO + " , " + PASSWORD );
        sql.append(" ) VALUES ( ");
        sql.append(" 'admin' , 'admin' ); ");

        return sql.toString();
    }

    public static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UserContract.ID, user.getId());
        values.put(UserContract.USUARIO, user.getUsuario());
        values.put(UserContract.PASSWORD, user.getPassword());
        return values;
    }

    public static User bind(Cursor cursor){

        if(!cursor.isBeforeFirst() || cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setUsuario(cursor.getString(cursor.getColumnIndex(UserContract.USUARIO)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));
            return user;
        }
        return null;
    }

    public static List<User> bindList(Cursor cursor){
        List<User> user = new ArrayList<>();

        while(cursor.moveToNext()){
            user.add(bind(cursor));
        }
        return user;
    }


}
