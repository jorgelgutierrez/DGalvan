package gutierrez.jorge.dgalvan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;


public class FichaAlimenticiaCliente extends AppCompatActivity {


    Toolbar toolbar;
    EditText nombre, edad, alergias, carnes, verduras, leguminosas, cereales, tortipan, picante, pago;
    Spinner sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_alimenticia_cliente);

        //Iniciando toolbar...
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Escucha para ver si se presiono el back e ir atras...
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
                Boolean first = sharedPreferences.getBoolean("first",true);
                if(first){
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.coor_ajustes),"Para comenzar primero debes acompletar este apartado y guardar cambios", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    Intent intent = new Intent(FichaAlimenticiaCliente.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Instanceando vista..
        nombre = (EditText) findViewById(R.id.nombre);
        edad = (EditText) findViewById(R.id.edad);
        sexo = (Spinner) findViewById(R.id.sexo);
        alergias = (EditText) findViewById(R.id.alergias);
        carnes = (EditText) findViewById(R.id.carnes);
        verduras = (EditText) findViewById(R.id.verduras);
        leguminosas = (EditText) findViewById(R.id.leguminosas);
        cereales = (EditText) findViewById(R.id.cereales);
        tortipan = (EditText) findViewById(R.id.tortipan);
        picante = (EditText) findViewById(R.id.picante);
        pago = (EditText) findViewById(R.id.pago);

        cargarPreferencias();

    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        Boolean first = sharedPreferences.getBoolean("first",true);
        if(first){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.coor_ajustes),"Para comenzar primero debes acompletar este apartado y guardar cambios", Snackbar.LENGTH_LONG);
            snackbar.show();
        }else{
            Intent intent = new Intent(FichaAlimenticiaCliente.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Metodo si es la primera vez que se abre la app redirecciona a ajustes...
    public void cargarPreferencias(){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        nombre.setText(sharedPreferences.getString("Nombre",""));
        sexo.setId(sharedPreferences.getInt("Sexo",0));
        edad.setText(sharedPreferences.getString("Edad",""));
        alergias.setText(sharedPreferences.getString("Alergias",""));
        carnes.setText(sharedPreferences.getString("Carnes",""));
        verduras.setText(sharedPreferences.getString("Verduras",""));
        leguminosas.setText(sharedPreferences.getString("Leguminosas",""));
        cereales.setText(sharedPreferences.getString("Cereales",""));
        cereales.setText(sharedPreferences.getString("Tortipan",""));
        picante.setText(sharedPreferences.getString("Picante",""));
        pago.setText(sharedPreferences.getString("Pago",""));



    }

    public void GuardarFichaAlimenticia(View guardar){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Nombre",nombre.getText().toString());
        editor.putString("Edad",edad.getText().toString());
        int sexofinal;
        sexofinal = sexo.getSelectedItemPosition();
        if(sexofinal == 1){
            editor.putString("Sexo","Femenino");
        }else if(sexofinal == 2){
            editor.putString("Sexo","Masculino");
        }else{
            editor.putString("Sexo","Ninguno");
        }
        editor.putString("Alergias", alergias.getText().toString());
        editor.putString("Carnes", carnes.getText().toString());
        editor.putString("Verduras", verduras.getText().toString());
        editor.putString("Leguminosas", leguminosas.getText().toString());
        editor.putString("Cereales", cereales.getText().toString());
        editor.putString("Tortipan", tortipan.getText().toString());
        editor.putString("Picante", picante.getText().toString());
        editor.putString("Pago", pago.getText().toString());
        editor.commit();
        new enviarFichaAlimenticiaCliente().execute();
    }


    //Asyntask para enviar fechas de acuerdo a su fecha de inicio de ss...
    class enviarFichaAlimenticiaCliente extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(FichaAlimenticiaCliente.this);
            pDialog.setMessage("   Cargando");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);


            String url_servidor = getResources().getString(R.string.url_servidor)+"Guardar_Ficha_Alimenticia_Cliente.php";
            HttpClient cliente = new DefaultHttpClient();
            HttpPost post = new HttpPost(url_servidor);
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>(1);
            postParameters.add(new BasicNameValuePair("token",sharedPreferences.getString("token","ninguno")));
            postParameters.add(new BasicNameValuePair("nombre",sharedPreferences.getString("Nombre","ninguno")));
            postParameters.add(new BasicNameValuePair("edad",sharedPreferences.getString("Edad","Ninguna")));
            postParameters.add(new BasicNameValuePair("sexo",sharedPreferences.getString("Sexo","Ninguna")));
            postParameters.add(new BasicNameValuePair("alergias",sharedPreferences.getString("Alergias","Ninguna")));
            postParameters.add(new BasicNameValuePair("carnes",sharedPreferences.getString("Carnes","Ninguna")));
            postParameters.add(new BasicNameValuePair("verduras",sharedPreferences.getString("Verduras","Ninguna")));
            postParameters.add(new BasicNameValuePair("leguminosas",sharedPreferences.getString("Leguminosas","Ninguna")));
            postParameters.add(new BasicNameValuePair("cereales",sharedPreferences.getString("Cereales","Ninguna")));
            postParameters.add(new BasicNameValuePair("tortipan",sharedPreferences.getString("Tortipan","Ninguna")));
            postParameters.add(new BasicNameValuePair("picante",sharedPreferences.getString("Picante","Ninguna")));
            postParameters.add(new BasicNameValuePair("pago",sharedPreferences.getString("Pago","Ninguna")));
            try {
                post.setEntity(new UrlEncodedFormEntity(postParameters, HTTP.UTF_8));
                cliente.execute(post);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            //Metodo si es la primera vez que se abre la app redirecciona a ajustes...
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            Boolean first = sharedPreferences.getBoolean("first",true);
            if(first){
                Intent intent = new Intent(FichaAlimenticiaCliente.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                onBackPressed();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first",false);
            editor.commit();
            Toast.makeText(getApplicationContext(),"Analisis Alimenticio Guardado",Toast.LENGTH_SHORT).show();
        }
    }


}
