package diana.orrego.calculadorabasica_d;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;


public class agregar_producto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        FloatingActionButton btnMostrarProducto = findViewById(R.id.btnMostrarProducto);
        btnMostrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarProducto();
            }
        });
        FloatingActionButton btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });
    }
    private void mostrarProducto(){
        Intent mostrarProducto = new Intent(agregar_producto.this, MainActivity.class);
        startActivity(mostrarProducto);
    }
    private void guardarProducto(){
        TextView tempVal = findViewById(R.id.txtCodigoProducto);
        String codigo = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtNombreProducto);
        String nombre = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtMarcaProducto);
        String marca = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDescripcionProducto);
        String descripcion = tempVal.getText().toString();

        tempVal = findViewById(R.id.txtDuiPersona);
        String dui = tempVal.getText().toString();

        try {
            JSONObject datosProducto = new JSONObject();
            datosProducto.put("codigo", codigo);
            datosProducto.put("nombre", nombre);
            datosProducto.put("marca", marca);
            datosProducto.put("descripcion", descripcion);
            datosProducto.put("dui", dui);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private class enviarDatosProducto extends AsyncTask<String,String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... parametros) {
            StringBuilder stringBuilder = new StringBuilder();
            String jsonResponse = null;
            String jsonDatos = parametros[0];
            BufferedReader reader;
            try {
                URL url = new URL("http://192.168.1.15:5984/db_agenda/");
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Accept","application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(jsonDatos);
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
            }catch (Exception ex){
                //
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}