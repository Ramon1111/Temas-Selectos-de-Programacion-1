package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView valor;
    TextView clean, mas, siete, ocho, nueve, menos, cuatro, cinco, seis, por, uno, dos, tres, entre, cero, punto, igual;

    Boolean nuevoCalculo, menosEscrito;
    Boolean nuevoNumero, fin;
    Double primerNumero;
    Integer lenghtPrimerNumero;
    Double segundoNumero;
    String operacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nuevoCalculo=true;
        nuevoNumero=true;
        primerNumero=null;
        lenghtPrimerNumero=null;
        segundoNumero=null;
        operacion=null;
        fin=false;
        menosEscrito=false;

        valor=(TextView)findViewById(R.id.valorMain);

        clean = (TextView)findViewById(R.id.cleanMain);
        mas = (TextView)findViewById(R.id.masMain);
        siete = (TextView)findViewById(R.id.sieteMain);
        ocho = (TextView)findViewById(R.id.ochoMain);
        nueve = (TextView)findViewById(R.id.nueveMain);
        menos = (TextView)findViewById(R.id.menosMain);
        cuatro = (TextView)findViewById(R.id.cuatroMain);
        cinco = (TextView)findViewById(R.id.cincoMain);
        seis = (TextView)findViewById(R.id.seisMain);
        por = (TextView)findViewById(R.id.porMain);
        uno = (TextView)findViewById(R.id.unoMain);
        dos = (TextView)findViewById(R.id.dosMain);
        tres = (TextView)findViewById(R.id.tresMain);
        entre = (TextView)findViewById(R.id.entreMain);
        cero = (TextView)findViewById(R.id.ceroMain);
        punto = (TextView)findViewById(R.id.puntoMain);
        igual = (TextView)findViewById(R.id.igualMain);

        siete.setOnClickListener(this);
        ocho.setOnClickListener(this);
        nueve.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);

        cero.setOnClickListener(this);
        punto.setOnClickListener(this);
        igual.setOnClickListener(this);

        nuevoEvento();
    }

    @Override
    public void onClick(View v) {
        TextView elemento=(TextView)findViewById(v.getId());

        switch(elemento.getId()) {
            case R.id.cleanMain:
                Log.i("newOperation","Nuevo Cálculo");
                nuevoEvento();
                break;

            default:
                if(nuevoCalculo) {
                    if(elemento.getId()==punto.getId()){
                        punto.setOnClickListener(null);
                        punto.setBackgroundResource(R.color.colorSecondaryText);
                    }

                    if (v.getId() == mas.getId() || v.getId() == menos.getId() || v.getId() == por.getId() || v.getId() == entre.getId()) {

                        primerNumero = Double.parseDouble(""+valor.getText().toString());
                        lenghtPrimerNumero = elemento.length();
                        Log.i("newOperation", "operación "+lenghtPrimerNumero);
                        operacion=elemento.getText().toString();
                        lenghtPrimerNumero=elemento.length();
                        ponerGrisTeclas();
                        nuevoCalculo=false;
                        punto.setOnClickListener(this);
                        punto.setBackgroundResource(R.color.colorPrimaryLight);
                        //punto.setBackgroundResource(R.color.colorSecondaryText);
                    }
                    if(elemento.getId()==igual.getId()){
                        igual.setBackgroundResource(R.color.colorSecondaryText);
                        igual.setOnClickListener(null);
                        ponerGrisTeclas();
                        nuevoCalculo=false;
                        fin=true;
                        noActionNumber();
                    }
                }
                else{
                    String segundo=valor.getText().toString().substring(lenghtPrimerNumero+1);
                    Double total=null;
                    Log.i("numero",""+primerNumero);
                    if(elemento.getId()==igual.getId()&& !segundo.isEmpty()&&!nuevoCalculo){
                        fin=true;
                        segundoNumero=Double.parseDouble(segundo);
                        switch (operacion){
                            case "+":
                                total=primerNumero+segundoNumero;
                                break;
                            case "-":
                                total=primerNumero-segundoNumero;
                                break;
                            case "x":
                                total=primerNumero*segundoNumero;
                                break;
                            case "/":
                                if(segundoNumero==0)
                                    Toast.makeText(getApplicationContext(),"No se puede realizar la división entre cero",Toast.LENGTH_SHORT).show();
                                else
                                    total=primerNumero/segundoNumero;
                                break;
                        }
                        if(total!=null){
                            igual.setBackgroundResource(R.color.colorSecondaryText);
                            igual.setOnClickListener(null);
                            valor.setText(String.valueOf(total));
                            noActionNumber();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Escriba el segundo numero a operar", Toast.LENGTH_SHORT).show();
                }


                if(!fin){
                    if(nuevoNumero){
                        nuevoNumero=false;
                        valor.setText(elemento.getText());
                    }
                    else{
                        valor.setText(valor.getText().toString().concat(elemento.getText().toString()));
                    }
                }
                break;
        }
    }

    public void nuevoEvento(){
        valor.setText("0");
        primerNumero=null;
        segundoNumero=null;
        operacion=null;
        nuevoCalculo=true;
        nuevoNumero=true;
        fin=false;

        clean.setOnClickListener(this);
        mas.setOnClickListener(this);
        menos.setOnClickListener(this);
        por.setOnClickListener(this);
        entre.setOnClickListener(this);
        igual.setOnClickListener(this);

        siete.setOnClickListener(this);
        ocho.setOnClickListener(this);
        nueve.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);

        cero.setOnClickListener(this);
        punto.setOnClickListener(this);

        mas.setBackgroundResource(R.color.colorPrimaryDark);
        menos.setBackgroundResource(R.color.colorPrimaryDark);
        por.setBackgroundResource(R.color.colorPrimaryDark);
        entre.setBackgroundResource(R.color.colorPrimaryDark);
        igual.setBackgroundResource(R.color.colorPrimaryDark);
    }

    public void ponerGrisTeclas(){
        mas.setBackgroundResource(R.color.colorSecondaryText);
        menos.setBackgroundResource(R.color.colorSecondaryText);
        por.setBackgroundResource(R.color.colorSecondaryText);
        entre.setBackgroundResource(R.color.colorSecondaryText);

        mas.setOnClickListener(null);
        menos.setOnClickListener(null);
        por.setOnClickListener(null);
        entre.setOnClickListener(null);
    }

    public void noActionNumber(){
        siete.setOnClickListener(null);
        ocho.setOnClickListener(null);
        nueve.setOnClickListener(null);
        cuatro.setOnClickListener(null);
        cinco.setOnClickListener(null);
        seis.setOnClickListener(null);
        uno.setOnClickListener(null);
        dos.setOnClickListener(null);
        tres.setOnClickListener(null);

        cero.setOnClickListener(null);
        punto.setOnClickListener(null);
    }


}
