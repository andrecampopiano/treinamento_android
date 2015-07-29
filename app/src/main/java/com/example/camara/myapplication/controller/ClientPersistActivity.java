package com.example.camara.myapplication.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    private void bindParameters(){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            client = (Client)extras.getParcelable(CLIENT_PARAM);
            if(client == null){
                throw new IllegalArgumentException();
            }
            bindClientForm();
        }
    }

    private void bindFields(){
        age = (EditText) findViewById(R.id.editTextAge);
        name = (EditText) findViewById(R.id.editTextName);
        address = (EditText) findViewById(R.id.editTextAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);

        cep = (EditText) findViewById(R.id.editTextCep);
        tipoLogradouro = (EditText) findViewById(R.id.editTextTipoLogradouro);
        logradouro = (EditText) findViewById(R.id.editTextLogradouro);
        bairro = (EditText) findViewById(R.id.editTextBairro);
        cidade = (EditText) findViewById(R.id.editTextCidade);
        estado = (EditText) findViewById(R.id.editTextEstado);
        bindButtonFindCep();
    }

    private void bindButtonFindCep(){
        btnFind = (Button) findViewById(R.id.btnCep);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAddressByCep().execute(cep.getText().toString());
            }
        });
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
                Toast.makeText(ClientPersistActivity.this, getString(R.string.success),Toast.LENGTH_LONG).show();
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient() {
        if(client == null){
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

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress>{

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
            tipoLogradouro.setText(clientAddress.getTipoDeLogradouro());
            logradouro.setText(clientAddress.getLogradouro());
            bairro.setText(clientAddress.getBairro());
            cidade.setText(clientAddress.getCidade());
            estado.setText(clientAddress.getEstado());
            progressDialog.dismiss();
        }
    }



}
