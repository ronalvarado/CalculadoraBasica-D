package diana.orrego.calculadorabasica_d;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Conversores conversor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tbhConversor = (TabHost) findViewById(R.id.tbhConversores);
        tbhConversor.setup();
        String str = "Universal";
        tbhConversor.addTab(tbhConversor.newTabSpec(str).setIndicator(str, null).setContent(R.id.tabCONVERSORPROPIO));
        String str2 = "Area";
        tbhConversor.addTab(tbhConversor.newTabSpec(str2).setIndicator(str2, null).setContent(R.id.tabAREA));

    }
    public void procesar(View v) {
///
        TextView temp = (TextView) findViewById(R.id.etUNIDADES);
        String str = "";
        int UNIDADES = temp.getText().toString().equals(str) ? 1 : Integer.parseInt(temp.getText().toString());
        TextView temp2 = (TextView) findViewById(R.id.etCANTIDAD);
        TextView valor = (TextView) findViewById(R.id.etUNIDADES2);
        String str2 = "/";
        if (!temp2.getText().toString().equals(str)) {
            int CANTIDAD = Integer.parseInt(temp2.getText().toString());
            int CAJAS = CANTIDAD / UNIDADES;
            StringBuilder sb = new StringBuilder();
            sb.append(CAJAS);
            sb.append(str2);
            sb.append(CANTIDAD % UNIDADES);
            valor.setText(sb.toString());
        }
        else if (!valor.getText().toString().equals(str))
        {
            String[] data = valor.getText().toString().split(str2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append((Integer.parseInt(data[0]) * UNIDADES) + Integer.parseInt(data[1]));
            temp2.setText(sb2.toString());
        }


    }

    public void convertir(View v) {
        try {
            this.conversor = new Conversores();
            int DE = ((Spinner) findViewById(R.id.spnDe)).getSelectedItemPosition();
            int A = ((Spinner) findViewById(R.id.spnA)).getSelectedItemPosition();

            double CANTIDAD = Double.parseDouble(((TextView) findViewById(R.id.etCANTIDADA)).getText().toString());
            TextView tempVal = (TextView) findViewById(R.id.lblRESPUESTA);
            StringBuilder sb = new StringBuilder();
            sb.append("La Respuesta: ");
            sb.append(this.conversor.convertir_area(DE, A, CANTIDAD));
            tempVal.setText(sb.toString());

        }catch (Exception err){
            TextView temp = (TextView) findViewById(R.id.lblRESPUESTA);
            temp.setText("ingrese una cantidad");
            Toast.makeText(getApplicationContext(),"por favor ingrese una cantidad",Toast.LENGTH_LONG).show();
        }
    }















}