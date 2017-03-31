package io.github.guilhermebferreira.pesoideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadoActivity extends AppCompatActivity {


    private TextView resultDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent intent = getIntent();
        String text = intent.getStringExtra("msg");
        Log.d("Received", "msg" + text);
        Toast.makeText(getApplicationContext(), "Received" + text, Toast.LENGTH_SHORT);

        resultDisplay = (TextView) findViewById(R.id.resultado);
        resultDisplay.setText(text);
    }
}
