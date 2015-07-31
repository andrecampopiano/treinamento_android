package com.example.camara.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.protocol.HTTP;

import java.util.List;


public class ClientListActivity extends AppCompatActivity {

    private static final String TAG = ClientListActivity.class.getSimpleName();
    private ListView listViewClient;
    private Client client;
    private FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindClientList();
        bindFab();
    }

    private void bindFab() {

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
                startActivity(intent);
            }
        });
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

    private void bindClientList() {
        List<Client> clients = getClients();
        listViewClient = (ListView) findViewById(R.id.listViewClients);
        ClientListAdapter clientListAdapter = new ClientListAdapter(ClientListActivity.this, clients);
        listViewClient.setAdapter(clientListAdapter);

        listViewClient.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return true; //false executa o click long e o click curto, true executa o long e remove o curto (talvez seja vice-versa)
            }
        });

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getItemAtPosition(position);
                // Best Practices: http://stackoverflow.com/questions/4275678/how-to-make-phone-call-using-intent-in-android
                final Intent goToSOPhoneCall = new Intent(Intent.ACTION_CALL /* or Intent.ACTION_DIAL (no manifest permission needed) */);
                goToSOPhoneCall.setData(Uri.parse("tel:" + client.getPhone()));
                startActivity(goToSOPhoneCall);
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
        if (item.getItemId() == R.id.menuEdit) {
            Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
            intent.putExtra(ClientPersistActivity.CLIENT_PARAM, (Parcelable) client);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuDelete) {
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
        if (item.getItemId() == R.id.menuAdd) {
            // Create the text message with a string
            final Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Seu texto aqui...");
            sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

            // Create intent to show the chooser dialog
            final Intent chooser = Intent.createChooser(sendIntent, "Titulo Chooser");

            // Verify the original intent will resolve to at least one activity
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private List<Client> getClients() {
        return new Client().getAll();
    }


}
