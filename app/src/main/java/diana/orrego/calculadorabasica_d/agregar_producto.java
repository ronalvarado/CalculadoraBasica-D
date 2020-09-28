package diana.orrego.calculadorabasica_d;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class agregar_producto extends AppCompatActivity {
    DB miDB;
    String accion = "nuevo";
    String idProducto = "0";
    ImageView imgFotoProducto;
    String urlCompletaImg;
    Button btnProductos;
    Intent takePictureIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        Button btnGuardarProducto= (Button)findViewById(R.id.btnGuardarProducto);
        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tempVal = (TextView)findViewById(R.id.txtNombre_producto);
                String nombre = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtMarca_producto);
                String marca = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtDescripcion_producto);
                String descripcion = tempVal.getText().toString();

                tempVal = (TextView)findViewById(R.id.txtPrecio_producto);
                String precio = tempVal.getText().toString();

                String[] data = {idProducto,nombre,marca,descripcion,precio};

                miDB = new DB(getApplicationContext(),"", null, 1);
                miDB.mantenimientoproductos(accion, data);

                Toast.makeText(getApplicationContext(),"Registro de producto se ha realizado correctamente!", Toast.LENGTH_LONG).show();
                mostrarListaProducto();
            }
        });
        btnGuardarProducto = (Button)findViewById(R.id.btnMostrarProducto);
        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProducto();
            }
        });
        mostrarDatosProducto();
    }
    void mostrarListaProducto(){
        Intent MostrarProducto = new Intent(agregar_producto.this, MainActivity.class);
        startActivity(MostrarProducto);
    }
    void mostrarDatosProducto(){
        try {
            Bundle recibirParametros = getIntent().getExtras();
            accion = recibirParametros.getString("accion");
            if (accion.equals("modificar")){
                String[] dataProducto = recibirParametros.getStringArray("dataProducto");

                idProducto = dataProducto[0];

                TextView tempVal = (TextView)findViewById(R.id.txtNombre_producto);
                tempVal.setText(dataProducto[1]);

                tempVal = (TextView)findViewById(R.id.txtMarca_producto);
                tempVal.setText(dataProducto[2]);

                tempVal = (TextView)findViewById(R.id.txtDescripcion_producto);
                tempVal.setText(dataProducto[3]);

                tempVal = (TextView)findViewById(R.id.txtPrecio_producto);
                tempVal.setText(dataProducto[4]);
            }
        }catch (Exception ex){
            ///
        }
    }
}