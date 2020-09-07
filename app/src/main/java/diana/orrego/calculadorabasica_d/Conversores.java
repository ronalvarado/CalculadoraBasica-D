package diana.orrego.calculadorabasica_d;


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
