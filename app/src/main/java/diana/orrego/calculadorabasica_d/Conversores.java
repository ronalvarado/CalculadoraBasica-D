package diana.orrego.calculadorabasica_d;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Conversores extends AppCompatActivity {
        double[] valores = {10.7639, 1.1963081929167, 1.19599, 1, 0.0015903307888, 0.0001434, 1e-4};

        Conversores() {
        }

        public double convertir_area(int de, int a, double cantidad) {

            DecimalFormat twoDForm = new DecimalFormat("#.##");
            double[] dArr = this.valores;
            return Double.parseDouble(twoDForm.format((dArr[a] / dArr[de]) * cantidad));
        }


}
