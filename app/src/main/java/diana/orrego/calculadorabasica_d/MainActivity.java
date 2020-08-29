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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 1.- Variables
    // hace la comunicación con el hardware
    SensorManager sensorManager;
    // para representar al sensor
    Sensor sensor;
    // para determinar si algo de acerca al dispositivo
    SensorEventListener sensorEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enlace de la variable TextView con la vista
        final TextView texto = (TextView)findViewById(R.id.tvSensor);
        // 2.- Aplicando el servicio
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // El tipo de sensor que se utiliza
        sensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // Verificar si el dispositivo tiene este tipo de sensor.
        // si no lo tiene hayq e terminar la acción
        if(sensor==null)finish();
        // llamamos al evento Listener para determinar determinar los cambios
                sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < sensor.getMaximumRange()){

                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    texto.setText("CAMBIANDO A COLOR ROJO");
                }else{

                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    texto.setText("SENSOR DE PROXIMIDAD");
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        }; // Agregamos el punto y coma
        // 4.-
        start();
    }//End: OnCreate.
    // 3.-
    public void start(){

        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }
    // Estos dos métodos se agregaron haciendo clic derecho en este punto
// y buscando en la lista de métodos estos
    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}