package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
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
    TextView clean, mas, siete, ocho, nueve, menos, cuatro, cinco, seis, por, uno, dos, tres, entre, cero, punto, igual, seno, coseno, tangente;

    Boolean nuevoCalculo, menosEscrito;
    Boolean nuevoNumero, fin;
    Double primerNumero;
    Integer lengthPrimerNumero;
    Double segundoNumero;
    String operacion;

    int orientacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nuevaImagen(); //Configura como si se fuera a hacer un nuevo calculo desde el inicio
        nuevoEvento();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {     //Si está en horizontal
            setContentView(R.layout.activity_main);                             //Vuelve a cargar la vista del activity
            nuevaImagen();
            nuevoEvento();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){   //Si está en vertical

            seno.setOnClickListener(null);                                      //Quita los eventos de OnClick
            coseno.setOnClickListener(null);
            tangente.setOnClickListener(null);

            setContentView(R.layout.activity_main);                             //Vuelve a cargar la vista del Activity

            nuevaImagen();
            nuevoEvento();
        }
    }

    @Override
    public void onClick(View v) {

        TextView elemento=(TextView)findViewById(v.getId());

        switch(elemento.getId()) {
            case R.id.cleanMain:
                nuevoEvento();
                break;
            case (R.id.sinMain):
                try{
                    Double seno=Math.sin(Math.toRadians(Double.parseDouble(""+valor.getText().toString())));                //Para usarlo en grados
                    valor.setText(String.valueOf(seno));
                    noActionNumber();
                    ponerGrisTeclas();
                    igual.setBackgroundResource(R.color.colorSecondaryText);
                    igual.setOnClickListener(null);
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Formato de número inválido", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.cosMain):
                try{
                    Double coseno=Math.cos(Math.toRadians(Double.parseDouble(""+valor.getText().toString())));              //Para usarlo en grados
                    valor.setText(String.valueOf(coseno));
                    noActionNumber();
                    ponerGrisTeclas();
                    igual.setBackgroundResource(R.color.colorSecondaryText);
                    igual.setOnClickListener(null);
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Formato de número inválido", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.tanMain):
                try{
                    Double tangente=Math.tan(Math.toRadians(Double.parseDouble(""+valor.getText().toString())));            //Para usarlo en grados
                    valor.setText(String.valueOf(tangente));
                    noActionNumber();
                    ponerGrisTeclas();
                    igual.setBackgroundResource(R.color.colorSecondaryText);
                    igual.setOnClickListener(null);
                }
                catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Formato de número inválido", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                if(nuevoCalculo) {
                    if(elemento.getId()==punto.getId())
                        punto.setOnClickListener(null);

                    if (v.getId() == mas.getId() || v.getId() == menos.getId() || v.getId() == por.getId() || v.getId() == entre.getId()) {
                        if(valor.getText().charAt(0)=='.'&&valor.getText().length()==1)
                            valor.setText("0.");
                        primerNumero = Double.parseDouble(""+valor.getText().toString());

                        lengthPrimerNumero = valor.length();
                        operacion=elemento.getText().toString();
                        Log.i("numero1",""+operacion);
                        ponerGrisTeclas();
                        nuevoCalculo=false;
                        punto.setOnClickListener(this);
                    }
                    if(elemento.getId()==igual.getId()){
                        if(valor.getText().charAt(0)=='.'&&valor.getText().length()==1)
                            valor.setText("0.");
                        igual.setBackgroundResource(R.color.colorSecondaryText);
                        igual.setOnClickListener(null);
                        ponerGrisTeclas();
                        nuevoCalculo=false;
                        fin=true;
                        noActionNumber();
                    }
                }
                else{

                    Double total=null;
                    if(elemento.getId()==igual.getId()&&!valor.getText().toString().substring(lengthPrimerNumero+1).isEmpty()){
                        fin=true;
                        String segundo=valor.getText().toString().substring(lengthPrimerNumero);
                        if((valor.getText().toString().substring(lengthPrimerNumero+1).charAt(0)=='.'&&valor.getText().toString().substring(lengthPrimerNumero+1).length()==1)||valor.getText().toString().substring(lengthPrimerNumero+1).isEmpty())
                            valor.setText(valor.getText().toString().substring(0, lengthPrimerNumero+1)+"0.");
                        segundoNumero=Double.parseDouble(valor.getText().toString().substring(lengthPrimerNumero+1));

                        switch (operacion){
                            case "+":
                                total=primerNumero+segundoNumero;
                                break;
                            case "-":
                                total=(Double)primerNumero-(Double)segundoNumero;

                                break;
                            case "x":
                                try {
                                    total = (Double) ((Double) primerNumero * (Double) segundoNumero);
                                }
                                catch (Exception e){
                                    Log.e("numero3",""+e.getMessage());
                                }
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
                        if(elemento.getId()==igual.getId()&&valor.getText().toString().substring(lengthPrimerNumero+1).isEmpty())
                            Toast.makeText(getApplicationContext(),"Escriba el segundo numero a operar", Toast.LENGTH_SHORT).show();
                }


                if(!fin&&elemento.getId()!=R.id.igualMain){
                    if(nuevoNumero){
                        nuevoNumero=false;
                        if (v.getId() == mas.getId() || v.getId() == menos.getId() || v.getId() == por.getId() || v.getId() == entre.getId())
                            valor.setText("0"+elemento.getText());
                        else
                            valor.setText(elemento.getText());
                    }
                    else
                         valor.setText(valor.getText().toString().concat(elemento.getText().toString()));
                }
                break;
        }
    }

    public void nuevaImagen(){                                          //Evento para gargar cuando se inicializa la vista
        orientacion=getResources().getConfiguration().orientation;

        nuevoCalculo=true;
        nuevoNumero=true;
        primerNumero=null;
        lengthPrimerNumero=null;
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

        seno = (TextView)findViewById(R.id.sinMain);            //Se queda así por si es que está en horizontal desde el inicio
        coseno = (TextView)findViewById(R.id.cosMain);
        tangente = (TextView)findViewById(R.id.tanMain);
    }

    public void nuevoEvento(){                              //Evento para cuando se hace un nuevo cálculo (se presiona AC)
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
        igual.setBackgroundResource(R.color.colorAccent);

        if(orientacion== Configuration.ORIENTATION_LANDSCAPE){
            seno.setOnClickListener(this);
            coseno.setOnClickListener(this);
            tangente.setOnClickListener(this);

            seno.setBackgroundResource(R.color.colorPrimaryDark);
            coseno.setBackgroundResource(R.color.colorPrimaryDark);
            tangente.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }

    public void ponerGrisTeclas(){                                                  //Bloquea las teclas de las operaciones
        mas.setBackgroundResource(R.color.colorSecondaryText);
        menos.setBackgroundResource(R.color.colorSecondaryText);
        por.setBackgroundResource(R.color.colorSecondaryText);
        entre.setBackgroundResource(R.color.colorSecondaryText);

        mas.setOnClickListener(null);
        menos.setOnClickListener(null);
        por.setOnClickListener(null);
        entre.setOnClickListener(null);

        if(orientacion== Configuration.ORIENTATION_LANDSCAPE){
            seno.setBackgroundResource(R.color.colorSecondaryText);
            coseno.setBackgroundResource(R.color.colorSecondaryText);
            tangente.setBackgroundResource(R.color.colorSecondaryText);

            seno.setOnClickListener(null);
            coseno.setOnClickListener(null);
            tangente.setOnClickListener(null);
        }
    }

    public void noActionNumber(){                               //Bloquea los eventos de los números cuando ya se ha hecho una operación
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


        if(orientacion== Configuration.ORIENTATION_LANDSCAPE){
            seno.setOnClickListener(null);
            coseno.setOnClickListener(null);
            tangente.setOnClickListener(null);
        }

    }


}
