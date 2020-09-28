package diana.orrego.calculadorabasica_d;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DB miBD;
    Cursor misProductos;
    Productos Producto;
    ArrayList<Productos> stringArrayList = new ArrayList<Productos>();
    ArrayList<Productos> copyStringArrayList = new ArrayList<Productos>();
    ListView ltsProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAgregarProducto = (FloatingActionButton)findViewById(R.id.btnAgregarProducto);
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar_productos("nuevo", new String[]{});
            }
        });
        obtenerDatosProducto();
        buscarProducto();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_producto, menu);

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        misProductos.moveToPosition(adapterContextMenuInfo.position);
        menu.setHeaderTitle(misProductos.getString(1));
    }
    void buscarProducto(){
        final TextView tempVal = (TextView)findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    stringArrayList.clear();
                    if (tempVal.getText().toString().trim().length() < 1) {//no hay texto para buscar
                        stringArrayList.addAll(copyStringArrayList);
                    } else {//hacemos la busqueda
                        for (Productos am : copyStringArrayList) {
                            String nombre = am.getNombre();
                            if (nombre.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())) {
                                stringArrayList.add(am);
                            }
                        }
                    }
                    adaptadorImagenes adaptadorImg = new adaptadorImagenes(getApplicationContext(), stringArrayList);
                    ltsProducto.setAdapter(adaptadorImg);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnxAgregar:
                agregar_productos("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataProducto = {
                        misProductos.getString(0),//idProducto
                        misProductos.getString(1),//nombre
                        misProductos.getString(2),//marca
                        misProductos.getString(3),//descripcion
                        misProductos.getString(4), //precio
                        misProductos.getString(5)  //urlImg
                };
                agregar_productos("modificar",dataProducto);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarFriend =  eliminarProducto();
                eliminarFriend.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    AlertDialog eliminarProducto(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(misProductos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el registro del producto?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miBD.mantenimientoproductos("eliminar",new String[]{misProductos.getString(0)});
                obtenerDatosProducto();
                Toast.makeText(getApplicationContext(), "producto eliminado con exito.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Eliminacion cancelada por el usuario.",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        return confirmacion.create();
    }
    void obtenerDatosProducto(){
        miBD = new DB(getApplicationContext(), "", null, 1);
        misProductos = miBD.mantenimientoproductos("consultar", null);
        if( misProductos.moveToFirst() ){ //hay registro en la BD que mostrar
            mostrarDatosProducto();
        } else{ //No tengo registro que mostrar.
            Toast.makeText(getApplicationContext(), "No hay registros de productos que mostrar",Toast.LENGTH_LONG).show();
            agregar_productos("nuevo", new String[]{});
        }
    }
    void agregar_productos(String accion, String[] dataProducto){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("dataProducto",dataProducto);
        Intent  agregar_producto = new Intent(MainActivity.this, agregar_producto.class);
        agregar_producto.putExtras(enviarParametros);
        startActivity( agregar_producto);
    }
    void mostrarDatosProducto(){
        stringArrayList.clear();
        ltsProducto = (ListView)findViewById(R.id.ltsProducto);
        do {
            Producto = new Productos(misProductos.getString(0),misProductos.getString(1), misProductos.getString(2), misProductos.getString(3), misProductos.getString(4), misProductos.getString(5));
            stringArrayList.add(Producto);
        }while(misProductos.moveToNext());
        adaptadorImagenes adaptadorImg = new adaptadorImagenes(getApplicationContext(), stringArrayList);
        ltsProducto.setAdapter(adaptadorImg);

        copyStringArrayList.clear();//limpiamos la lista de amigos
        copyStringArrayList.addAll(stringArrayList);//creamos la copia de la lista de amigos...
        registerForContextMenu(ltsProducto);
    }
}
class Productos{
    String id;
    String nombre;
    String marca;
    String descripcion;
    String precio;
    String urlImg;

    public Productos(String id, String nombre, String marca, String descripcion, String precio, String urlImg) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImg = urlImg;
    }

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre;}

    public String getMarca() { return marca;}

    public void setMarca(String marca) { this.marca= marca; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion;}

    public String getPrecio() { return precio; }

    public void setPrecio(String precio) { this.precio = precio; }

    public String getUrlImg() { return urlImg; }

    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }
}