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

        imgFotoProducto = findViewById(R.id.imgFotoProducto);

        btnProductos = findViewById(R.id.btnMostrarProducto);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarListaProducto();
            }
        });
        guardarDatosProducto();
        mostrarDatosProducto();
        tomarFotoProducto();
    }
    void tomarFotoProducto(){
        imgFotoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //guardando la imagen
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (Exception ex){}
                    if (photoFile != null) {
                        try {
                            Uri photoURI = FileProvider.getUriForFile(agregar_producto.this, "com.example.diana.orrego.calculadorabasica_d", photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 1);
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error Toma Foto: "+ ex.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaImg);
                imgFotoProducto.setImageBitmap(imageBitmap);
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "imagen_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if( storageDir.exists()==false ){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        urlCompletaImg = image.getAbsolutePath();
        return image;

    }
    void guardarDatosProducto(){
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