package com.example.camara.myapplication.model.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.camara.myapplication.model.entities.User;
import com.example.camara.myapplication.util.AppUtil;

/**
 * Created by andre on 30/07/15.
 */
public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;
    private SQLiteUserRepository(){super();}

    public static UserRepository getInstance(){
        if(SQLiteUserRepository.singletonInstance == null){
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }
        return SQLiteUserRepository.singletonInstance;
    }


    @Override
    public Boolean findUser(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String where = UserContract.USUARIO + " = ? AND " + UserContract.PASSWORD + "= ?";
        String[] args = {user.getUsuario().toString() , user.getPassword().toString()};

        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, where, args, null, null,null);

        return cursor.getCount() > 0;

    }
}
