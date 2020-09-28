package diana.orrego.calculadorabasica_d;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    static String nameDB = "db_productos";
    static String tblProductos = "CREATE TABLE productos(idProducto integer primary key autoincrement, Nombre text, Marca text, Descripcion text, Precio text)";
    ////
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDB, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblProductos);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor mantenimientoproductos(String accion, String[] data){
        SQLiteDatabase sqLiteDatabaseReadable=getReadableDatabase();
        SQLiteDatabase sqLiteDatabaseWritable=getWritableDatabase();
        Cursor cursor = null;
        switch (accion){
            case "consultar":
                cursor=sqLiteDatabaseReadable.rawQuery("SELECT * FROM productos ORDER BY nombre ASC", null);
                break;
            case "nuevo":
                sqLiteDatabaseWritable.execSQL("INSERT INTO productos (nombre, marca, descripcion, Precio) VALUES('"+ data[1] +"','"+data[2]+"','"+data[3]+"','"+data[4]+"')");
                break;
            case "modificar":
                sqLiteDatabaseWritable.execSQL("UPDATE productos SET nombre='"+ data[1] +"',marca='"+data[2]+"',descripcion='"+data[3]+"',precio='"+data[4]+"' WHERE idProducto='"+data[0]+"'");
                break;
            case "eliminar":
                sqLiteDatabaseWritable.execSQL("DELETE FROM productos WHERE idProducto='"+ data[0] +"'");
                break;
        }
        return cursor;
    }
}
