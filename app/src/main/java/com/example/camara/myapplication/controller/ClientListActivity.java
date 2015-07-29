package com.example.camara.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.camara.myapplication.model.entities.Client;
import com.example.camara.myapplication.R;

import java.util.List;


public class ClientListActivity extends AppCompatActivity {

    private static final String TAG = ClientListActivity.class.getSimpleName();
    private ListView listViewClient;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindClientList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClient.getAdapter();
        adapter.setClients(Client.getAll());
        adapter.notifyDataSetChanged();
    }

    private void bindClientList(){
        List<Client> clients = getClients();
        listViewClient = (ListView) findViewById(R.id.listViewClients);
        ClientListAdapter clientListAdapter = new ClientListAdapter(ClientListActivity.this,clients);
        listViewClient.setAdapter(clientListAdapter);

        listViewClient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false; //false executa o click long e o click curto, true executa o long e remove o curto (talvez seja vice-versa)
            }
        });

        registerForContextMenu(listViewClient);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuEdit){
            Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            intent.putExtra(ClientPersistActivity.CLIENT_PARAM,(Parcelable) client);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuDelete){
            new AlertDialog.Builder(ClientListActivity.this)
                    .setMessage(R.string.confirm_process)
                    .setTitle(R.string.confirm_title)
                    .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            client.delete();
                            refreshClientList();
                            Toast.makeText(ClientListActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(R.string.confirm_no, null).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAdd) {
            Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Client> getClients(){
        return new Client().getAll();
    }


}
