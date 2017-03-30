package io.github.guilhermebferreira.pesoideal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

       /* mLoginFormView = findViewById(R.id.imc_form);
        mProgressView = findViewById(R.id.calc_progress);

        showProgress(true);*/

        setContentView(R.layout.activity_calcular_imc);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioSexo);
        btnDisplay = (Button) findViewById(R.id.calc_button);
        imcDisplay = (TextView) findViewById(R.id.imc_result);
        pesoText = (EditText) findViewById(R.id.peso);
        final String pesoStr = pesoText.getText().toString();
        idadeText = (EditText) findViewById(R.id.idade);
        final String idadeStr = idadeText.getText().toString();
        alturaText = (EditText) findViewById(R.id.altura);
        final String alturaStr = alturaText.getText().toString();

        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();


                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                if (idade > 15) {
                    //calculo adultos acima dos 15 anos
                    resultado = adultoResultado(imc);
                } else if (radioButton.getText().toString() == "Masculino") {
                    //calculo menino abaixo dos 15 anos
                    resultado = meninoResultado(idade, imc);
                } else if (radioButton.getText().toString() == "Feminino") {
                    //calculo menina abaixo dos 15 anos
                    resultado = meninaResultado(idade, imc);
                } else {
                    resultado = "Erro, verifique os dados informados";
                }


                imcDisplay.setText(resultado);


            }

        });

    }

    private void setWarning(String message) {
        Toast.makeText(CalcularIMC.this,
                message, Toast.LENGTH_SHORT).show();
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}




