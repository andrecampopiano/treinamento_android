package com.example.camara.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.camara.myapplication.R;
import com.example.camara.myapplication.model.entities.Client;
import com.example.camara.myapplication.model.entities.ClientAddress;
import com.example.camara.myapplication.model.services.CepService;
import com.example.camara.myapplication.util.FormHelper;

import org.apache.http.FormattedHeader;

public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private ClientAddress clientAddress;

    private EditText age;
    private EditText name;
    private EditText address;
    private EditText phone;
    private EditText cep;
    private Button btnFind;

    private EditText tipoLogradouro;
    private EditText logradouro;
    private EditText bairro;
    private EditText cidade;
    private EditText estado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);

        bindFields();
        bindParameters();

    }


    private void bindParameters() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client = (Client) extras.getParcelable(CLIENT_PARAM);
            if (client == null) {
                throw new IllegalArgumentException();
            }
            bindClientForm();
        }
    }

    private void bindFields() {

        name = (EditText) findViewById(R.id.editTextName);
        name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (name.getRight() - name.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
        age = (EditText) findViewById(R.id.editTextAge);
        address = (EditText) findViewById(R.id.editTextAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);

        cep = (EditText) findViewById(R.id.editTextCep);
        cep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_cep, 0);
        cep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (cep.getRight() - cep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (FormHelper.requiredValidade(ClientPersistActivity.this, cep) && cep.length() == 8) {
                            new GetAddressByCep().execute(cep.getText().toString());
                        } else {
                            Toast.makeText(ClientPersistActivity.this, R.string.cep_invalido, Toast.LENGTH_SHORT).show();
                        }


                    }
                }
                return false;
            }
        });

        tipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        logradouro = (EditText) findViewById(R.id.editTextLogradouro);
        bairro = (EditText) findViewById(R.id.editTextBairro);
        cidade = (EditText) findViewById(R.id.editTextCidade);
        estado = (EditText) findViewById(R.id.editTextEstado);

    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    name.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    phone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {


            if (FormHelper.requiredValidade(ClientPersistActivity.this, name, age, address, phone)) {
                bindClient();
                client.save();
                Toast.makeText(ClientPersistActivity.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient() {
        if (client == null) {
            client = new Client();

        }
        client.setName(name.getText().toString());
        client.setAge(Integer.valueOf(age.getText().toString()));
        client.setAddress(address.getText().toString());
        client.setPhone(phone.getText().toString());

        clientAddress = new ClientAddress();
        clientAddress.setCep(cep.getText().toString());
        clientAddress.setTipoDeLogradouro(tipoLogradouro.getText().toString());
        clientAddress.setLogradouro(logradouro.getText().toString());
        clientAddress.setBairro(bairro.getText().toString());
        clientAddress.setCidade(cidade.getText().toString());
        clientAddress.setEstado(estado.getText().toString());

        client.setClientAddress(clientAddress);
    }

    private void bindClientForm() {
        name.setText(client.getName());
        age.setText(client.getAge().toString());
        phone.setText(client.getPhone());
        address.setText(client.getAddress());

        cep.setText(client.getClientAddress().getCep());
        tipoLogradouro.setText(client.getClientAddress().getTipoDeLogradouro());
        logradouro.setText(client.getClientAddress().getLogradouro());
        bairro.setText(client.getClientAddress().getBairro());
        cidade.setText(client.getClientAddress().getCidade());
        estado.setText(client.getClientAddress().getEstado());
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            if (clientAddress != null) {

                tipoLogradouro.setText(clientAddress.getTipoDeLogradouro());
                logradouro.setText(clientAddress.getLogradouro());
                bairro.setText(clientAddress.getBairro());
                cidade.setText(clientAddress.getCidade());
                estado.setText(clientAddress.getEstado());
            }
            progressDialog.dismiss();
        }
    }


}
