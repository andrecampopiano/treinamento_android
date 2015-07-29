package com.example.camara.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.camara.myapplication.model.entities.Client;
import com.example.camara.myapplication.model.entities.ClientAddress;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    public static final String CEP = "cep";
    public static final String TIPO_LOGRADOURO = "tipo_logradouro";
    public static final String LOGRADOURO = "logradouro";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String ESTADO = "estado";


    public static final String[] COLUMNS = {ID, NAME, AGE, PHONE, ADDRESS,CEP, TIPO_LOGRADOURO,LOGRADOURO, BAIRRO, CIDADE, ESTADO };

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(CEP + " TEXT, ");
        sql.append(TIPO_LOGRADOURO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT, ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.ADDRESS, client.getAddress());
        values.put(ClientContract.CEP, client.getClientAddress().getCep());
        values.put(ClientContract.TIPO_LOGRADOURO, client.getClientAddress().getTipoDeLogradouro());
        values.put(ClientContract.LOGRADOURO, client.getClientAddress().getLogradouro());
        values.put(ClientContract.BAIRRO, client.getClientAddress().getBairro());
        values.put(ClientContract.CIDADE, client.getClientAddress().getCidade());
        values.put(ClientContract.ESTADO, client.getClientAddress().getEstado());

        return values;
    }

    public static List<Client> bindList(Cursor cursor){
        List<Client> clients = new ArrayList<>();
        Client client;
        while(cursor.moveToNext()){
            clients.add(bind(cursor));
        }
        return clients;
    }

    public static Client bind(Cursor cursor){

        if(!cursor.isBeforeFirst() || cursor.moveToFirst()){
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.setAddress(cursor.getString(cursor.getColumnIndex(ClientContract.ADDRESS)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));

            ClientAddress clientAddress = new ClientAddress();
            clientAddress.setCep(cursor.getString(cursor.getColumnIndex(ClientContract.CEP)));
            clientAddress.setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.TIPO_LOGRADOURO)));
            clientAddress.setLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.LOGRADOURO)));
            clientAddress.setBairro(cursor.getString(cursor.getColumnIndex(ClientContract.BAIRRO)));
            clientAddress.setCidade(cursor.getString(cursor.getColumnIndex(ClientContract.CIDADE)));
            clientAddress.setEstado(cursor.getString(cursor.getColumnIndex(ClientContract.ESTADO)));

            client.setClientAddress(clientAddress);
            return client;
        }
        return null;
    }



}
