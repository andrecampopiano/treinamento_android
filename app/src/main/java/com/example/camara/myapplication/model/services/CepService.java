package com.example.camara.myapplication.model.services;

import com.example.camara.myapplication.model.entities.ClientAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class CepService {

    private static final String URL = "http://correiosapi.apphb.com/cep/";

    private CepService(){
        super();
    }

    public static ClientAddress getAddressBy(String cep){

        ClientAddress address = null;
        try {
            URL url = new URL(URL+cep);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json"); // só aceita uma reposta se for json

            int respondeCode = conn.getResponseCode();

            if(respondeCode != HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Erro code: " + respondeCode);
            }

            InputStream inputStream = conn.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            address = objectMapper.readValue(inputStream, ClientAddress.class);
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

}
