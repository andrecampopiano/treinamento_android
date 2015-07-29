package com.example.camara.myapplication.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.camara.myapplication.model.entities.Client;
import com.example.camara.myapplication.R;

import java.util.List;

/**
 * Created by Camara on 20/07/2015.
 */
public class ClientListAdapter extends BaseAdapter {

    private Activity context;
    private List<Client> clientlist;

    public ClientListAdapter(Activity context, List<Client> clientList){
        this.clientlist = clientList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return clientlist.size();
    }

    @Override
    public Client getItem(int position) {
        return clientlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.client_list_item, parent, false);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(getItem(position).getName());

        TextView textViewAge = (TextView) view.findViewById(R.id.textViewAge);
        textViewAge.setText(getItem(position).getAge().toString());

        return view;
    }

    public void setClients(List<Client> clients){
        clientlist = clients;

    }
}
