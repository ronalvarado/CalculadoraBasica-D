package diana.orrego.calculadorabasica_d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void calcular(View view) {
        try {
            RadioGroup optOperaciones = (RadioGroup) findViewById(R.id.optOperaciones);
            Spinner cboOperaciones = (Spinner) findViewById(R.id.cboOperaciones);

            TextView tempval = (TextView) findViewById(R.id.txtNum1);
            double num1 = Double.parseDouble(tempval.getText().toString());

            tempval = (TextView) findViewById(R.id.txtNum2);
            double num2 = Double.parseDouble(tempval.getText().toString());

            double respuesta = 0;
            //Este es para el radiogroup y los radiobuttons
            switch (optOperaciones.getCheckedRadioButtonId()) {
                case R.id.optSuma:
                    respuesta = num1 + num2;
                    break;
                case R.id.optResta:
                    respuesta = num1 - num2;
                    break;
                case R.id.optMultiplicacion:
                    respuesta = num1 * num2;
                    break;
                case R.id.optDivision:
                    respuesta = num1 / num2;
                    break;
                case R.id.optPorcentaje:
                    respuesta = (num1 / num2) * num1;
                    break;
                case R.id.optExponenciacion:
                    respuesta = Math.pow(num1, num2);
                    break;
                case R.id.optModulo:
                    respuesta = num1 - (num1 / num2 * num2);
                    break;
                case R.id.optFactoreo:
                    respuesta = num1 / num2;
                    break;

            }
            //Este es para el spinner... -> ComboBox.
            switch (cboOperaciones.getSelectedItemPosition()) {
                case 1: //suma
                    respuesta = num1 + num2;
                    break;
                case 2: //resta
                    respuesta = num1 - num2;
                    break;
                case 3: //multiplicacion
                    respuesta = num1 * num2;
                    break;
                case 4: //division
                    respuesta = num1 / num2;
                    break;
                case 5: //porcentaje
                    respuesta = (num1 / num2) * num1;
                    break;
                case 6: //exponenciacion
                    respuesta = Math.pow(num1, num2);
                    break;
                case 7: //modulo
                    respuesta = num1 - (num1 / num2 * num2);
                    break;
                case 8: //factoreo
                    long factorial = 1;
                    respuesta = (num1 + num2) * factorial;
                    break;
            }
            tempval = (TextView) findViewById(R.id.lblRespuesta);
            tempval.setText("Respuesta: " + respuesta);
        } catch (Exception err) {
            TextView temp = (TextView) findViewById(R.id.lblRespuesta);
            temp.setText("por favor ingresar los datos correspondientes.");

            Toast.makeText(getApplicationContext(), "por favor ingrese los datos.", Toast.LENGTH_LONG).show();
        }
    }
}
