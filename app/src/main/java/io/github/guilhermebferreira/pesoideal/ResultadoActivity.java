package io.github.guilhermebferreira.pesoideal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {


    private TextView resultDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        resultDisplay = (TextView) findViewById(R.id.resultado);
        resultDisplay.setText("Resultado");
    }
}
