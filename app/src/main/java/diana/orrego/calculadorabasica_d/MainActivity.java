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
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> copyStringArrayList = new ArrayList<String>();
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAgregarProducto = (FloatingActionButton)findViewById(R.id.btnAgregarProducto);
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarProducto("nuevo", new String[]{});
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
                stringArrayList.clear();
                if( tempVal.getText().toString().trim().length()<1 ){//no hay texto para buscar
                    stringArrayList.addAll(copyStringArrayList);
                } else{//hacemos la busqueda
                    for (String producto : copyStringArrayList){
                        if(producto.toLowerCase().contains(tempVal.getText().toString().trim().toLowerCase())){
                            stringArrayList.add(producto);
                        }
                    }
                }
                stringArrayAdapter.notifyDataSetChanged();
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
                AgregarProducto("nuevo", new String[]{});
                return true;

            case R.id.mnxModificar:
                String[] dataProducto = {
                        misProductos.getString(0),//idProducto
                        misProductos.getString(1),//nombre_producto
                        misProductos.getString(2),//Marca_producto
                        misProductos.getString(3),//Descripcion_producto
                        misProductos.getString(4) //Precio_Producto
                };
                AgregarProducto("modificar",dataProducto);
                return true;

            case R.id.mnxEliminar:
                AlertDialog eliminarProducto =  eliminarProducto();
                eliminarProducto.show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    AlertDialog eliminarProducto(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(MainActivity.this);
        confirmacion.setTitle(misProductos.getString(1));
        confirmacion.setMessage("Esta seguro de eliminar el registro?");
        confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miBD.mantenimientoproductos("eliminar",new String[]{misProductos.getString(0)});
                obtenerDatosProducto();
                Toast.makeText(getApplicationContext(), "Amigo eliminado con exito.",Toast.LENGTH_SHORT).show();
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
            AgregarProducto("nuevo", new String[]{});
        }
    }
    void AgregarProducto(String accion, String[] dataProducto){
        Bundle enviarParametros = new Bundle();
        enviarParametros.putString("accion",accion);
        enviarParametros.putStringArray("dataProducto",dataProducto);
        Intent AgregarProductoActivity = new Intent(MainActivity.this, agregar_producto.class);
        AgregarProductoActivity.putExtras(enviarParametros);
        startActivity(AgregarProductoActivity);
    }
    void mostrarDatosProducto(){
        stringArrayList.clear();
        ListView ltsProducto = (ListView)findViewById(R.id.ltsProducto);
        stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        ltsProducto.setAdapter(stringArrayAdapter);
        do {
            stringArrayList.add(misProductos.getString(1));
        }while(misProductos.moveToNext());

        copyStringArrayList.clear();//limpiamos la lista de producto
        copyStringArrayList.addAll(stringArrayList);//creamos la copia de la lista de producto...

        stringArrayAdapter.notifyDataSetChanged();
        registerForContextMenu(ltsProducto);
    }
}
//prueva
