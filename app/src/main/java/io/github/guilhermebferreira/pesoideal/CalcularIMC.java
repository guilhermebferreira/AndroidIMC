package io.github.guilhermebferreira.pesoideal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class CalcularIMC extends AppCompatActivity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private TextView imcDisplay;
    private EditText pesoText;
    private EditText idadeText;
    private EditText alturaText;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calcular_imc);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioSexo);
        btnDisplay = (Button) findViewById(R.id.calc_button);
        pesoText = (EditText) findViewById(R.id.peso);
        idadeText = (EditText) findViewById(R.id.idade);
        alturaText = (EditText) findViewById(R.id.altura);

        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String pesoStr = pesoText.getText().toString();
                String alturaStr = alturaText.getText().toString();
                String idadeStr = idadeText.getText().toString();
                String sexoStr = "";

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                if (selectedId > 0) {
                    radioButton = (RadioButton) findViewById(selectedId);
                    sexoStr = radioButton.getText().toString();
                }


                double imc, peso, altura;
                int idade;
                imc = peso = altura = idade = 0;
                String resultado;

                if (TextUtils.isEmpty(pesoStr)) {

                    setWarning("Informe o valor do peso");
                } else if (TextUtils.isEmpty(alturaStr)) {

                    setWarning("Informe o valor da altura");
                } else if (TextUtils.isEmpty(idadeStr)) {

                    setWarning("Informe o valor da idade");
                }

                peso = Double.parseDouble(pesoStr);
                altura = Double.parseDouble(alturaStr);
                idade = Integer.parseInt(idadeStr);

                imc = peso / (Math.pow(altura, 2));




                if (idade > 15) {
                    //calculo adultos acima dos 15 anos
                    resultado = adultoResultado(imc);
                } else if (TextUtils.isEmpty(sexoStr)) {
                    resultado = "Necessário informar o sexo";
                } else {
                    if (sexoStr == "Masculino") {
                        //calculo menino abaixo dos 15 anos
                        Log.d("Send", "imc " + String.valueOf(imc));
                        Log.d("Send", "idade " + String.valueOf(idade));
                        Log.d("Send", "sexo " + "Masculino");
                        resultado = meninoResultado(idade, imc);
                    } else {// if (sexoStr == "Feminino") {
                        //calculo menina abaixo dos 15 anos
                        Log.d("Send", "imc " + String.valueOf(imc));
                        Log.d("Send", "idade " + String.valueOf(idade));
                        Log.d("Send", "sexo " + "Feminino");
                        resultado = meninaResultado(idade, imc);
                    }
                }

                Log.d("Send", "resultado" + resultado);

                setWarning("o IMC foi calculado");

                sendNotification("o IMC foi calculado.", resultado);


            }

        });

    }

    private void setWarning(String message) {
        Toast.makeText(CalcularIMC.this,
                message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String messageBody, String messageIntent) {
        Intent intent = new Intent(this, ResultadoActivity.class);
        Log.d("Send", "msg" + messageBody);
        Toast.makeText(getApplicationContext(), "msg" + messageBody, Toast.LENGTH_SHORT);
        intent.putExtra("msg", messageIntent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Peso Ideal")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


    public String adultoResultado(double imc) {
        //adulto
        //acima dos 15 anos
        String response;
        if (imc < 17) {
            //abaixo de 17
            response = "Muito abaixo do peso";
        } else if (imc < 18.5) {
            //entre 17 e 18,49
            response = "Abaixo do peso";
        } else if (imc < 25) {
            //entre 18,5 e 24,99
            response = "Peso normal";
        } else if (imc < 30) {
            //entre 25 e 29,99
            response = "Acima do peso";
        } else if (imc < 35) {
            //entre 30 e 34,99
            response = "Obesidade I";
        } else if (imc < 40) {
            //entre 35 e 39,99
            response = "Obesidade II (severa)";
        } else {
            //acima de 40
            response = "Obesidade III (mórbida)";
        }
        return response;
    }

    public String meninoResultado(int idade, double imc) {
        //menino
        // até os 15 anos
        String response;
        //a tabela base não disponibilizou informações que caracterizassem "Abaixo do peso"
        switch (idade) {
            case 6:
                response = this.imcInfantil(imc, 16.6, 18);
                break;
            case 7:
                response = this.imcInfantil(imc, 17.3, 19.1);
                break;
            case 8:
                response = this.imcInfantil(imc, 16.7, 20.3);//um pouco estranho esse ponto fora da curva
                break;
            case 9:
                response = this.imcInfantil(imc, 18.8, 21.4);
                break;
            case 10:
                response = this.imcInfantil(imc, 19.6, 22.5);
                break;
            case 11:
                response = this.imcInfantil(imc, 20.3, 23.7);
                break;
            case 12:
                response = this.imcInfantil(imc, 21.1, 24.8);
                break;
            case 13:
                response = this.imcInfantil(imc, 21.9, 25.9);
                break;
            case 14:
                response = this.imcInfantil(imc, 22.7, 26.9);
                break;
            case 15:
                response = this.imcInfantil(imc, 23.6, 27.7);
                break;
            default:
                response = "Erro, verifique os dados informados";
        }
        return response;
    }

    public String meninaResultado(int idade, double imc) {
        //menina
        // até os 15 anos
        String response;
        //a tabela base não disponibilizou informações que caracterizassem "Abaixo do peso"
        switch (idade) {
            case 6:
                response = this.imcInfantil(imc, 16.1, 17.4);
                break;
            case 7:
                response = this.imcInfantil(imc, 17.1, 18.9);
                break;
            case 8:
                response = this.imcInfantil(imc, 18.1, 20.3);
                break;
            case 9:
                response = this.imcInfantil(imc, 19.1, 21.7);
                break;
            case 10:
                response = this.imcInfantil(imc, 20.1, 23.2);
                break;
            case 11:
                response = this.imcInfantil(imc, 21.1, 24.5);
                break;
            case 12:
                response = this.imcInfantil(imc, 22.1, 25.9);
                break;
            case 13:
                response = this.imcInfantil(imc, 23, 27.7);
                break;
            case 14:
                response = this.imcInfantil(imc, 23.8, 27.9);
                break;
            case 15:
                response = this.imcInfantil(imc, 24.2, 28.8);
                break;
            default:
                response = "Erro, verifique os dados informados";
        }
        return response;
    }

    private String imcInfantil(double imc, double sobrepeso, double obesidade) {
        if (imc <= sobrepeso) {
            return "Peso normal";
        } else if (imc <= obesidade) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }


}




