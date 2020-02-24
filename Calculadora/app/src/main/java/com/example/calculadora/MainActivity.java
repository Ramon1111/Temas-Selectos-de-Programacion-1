package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valor=(TextView)findViewById(R.id.valorMain);

        TextView clear = (TextView)findViewById(R.id.cleanMain);
        TextView mas = (TextView)findViewById(R.id.masMain);
        TextView siete = (TextView)findViewById(R.id.sieteMain);
        TextView ocho = (TextView)findViewById(R.id.ochoMain);
        TextView nueve = (TextView)findViewById(R.id.nueveMain);
        TextView menos = (TextView)findViewById(R.id.menosMain);
        TextView cuatro = (TextView)findViewById(R.id.cuatroMain);
        TextView cinco = (TextView)findViewById(R.id.cincoMain);
        TextView seis = (TextView)findViewById(R.id.seisMain);
        TextView por = (TextView)findViewById(R.id.porMain);
        TextView uno = (TextView)findViewById(R.id.unoMain);
        TextView dos = (TextView)findViewById(R.id.dosMain);
        TextView tres = (TextView)findViewById(R.id.tresMain);
        TextView entre = (TextView)findViewById(R.id.entreMain);
        TextView cero = (TextView)findViewById(R.id.ceroMain);
        TextView punto = (TextView)findViewById(R.id.puntoMain);
        TextView igual = (TextView)findViewById(R.id.igualMain);

        clear.setOnClickListener(this);
        mas.setOnClickListener(this);
        siete.setOnClickListener(this);
        ocho.setOnClickListener(this);
        nueve.setOnClickListener(this);
        menos.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        por.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        entre.setOnClickListener(this);
        cero.setOnClickListener(this);
        punto.setOnClickListener(this);
        igual.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        TextView elemento=(TextView)findViewById(v.getId());

        if(valor.getText().toString()=="0"&&v.getId()!=R.id.cleanMain&&v.getId()!=R.id.igualMain)
            valor.setText(elemento.getText().toString());
        else{
            if(v.getId()==R.id.cleanMain)
                valor.setText("0");
            else{
                if(v.getId()==R.id.igualMain){
                    CharSequence cadena=valor.getText();
                    for(int i=0;i<cadena.length();i++){
                        Log.i("letra"+i,""+cadena.charAt(i));
                    }
                }
                else
                    valor.setText(valor.getText().toString()+elemento.getText().toString());
            }
        }


    }
}
