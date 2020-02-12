package com.example.miaplicacion2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.miaplicacion2.ClasesEjemplo.Administrador;
import com.example.miaplicacion2.ClasesEjemplo.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Usuario> usuariosLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int cont=0;
        while(cont<10){
            Log.i("Mensaje","Hola Mundo"+cont);
            cont++;
        }

        boolean valida= true;
        if(valida){
            Log.i("Booleano","Verdadero");
        }

        String cadena1="MiCadena";
        String cadena2="MiCadena";
        if(cadena1 == cadena2){
            Log.i("Cadena", "Las cadenas coinciden");
        }
        else{
            Log.i("Cadena", "Las cadenas no coinciden");
        }

        if(cadena1.compareTo(cadena2)==0){//Regresa cero (0) si es igual.
            Log.i("Cadena", "Las cadenas son iguales usando compareTo");
        }
        else{
            Log.i("Cadena", "Las cadenas no son iguales usando compareTo");
        }

        if(cadena1.equals(cadena2)){//Regresa un bopoleano dependiendo de la respuesta
            Log.i("Cadena", "Las cadenas son iguales equals");
        }
        else{
            Log.i("Cadena", "Las cadenas no son iguales  equals");
        }

        //Log.i("Mensaje","Hola Mundo");
        //Alt+Enter para obtneer información de opciones.
        //Manda a la bitácora de nuestro seguidor (?)

        LlenarUsuarios();
        RecorrerLista();

        AgregarAdministrador();

    }

    public void LlenarUsuarios(){
        usuariosLista = new ArrayList<>();
        usuariosLista.add(new Usuario("Ramoncito", "HelloThere!", (short)20, 1)); //Realizar casteo dependiendo del tipo de datos.
        usuariosLista.add(new Usuario("Fofo", "pollo", (short)12, 2));
        usuariosLista.add(new Usuario("Iago", "hat", (short)6, 3));
        usuariosLista.add(new Usuario("Tonita", "tortu", (short)21, 4));
        usuariosLista.add(new Usuario("Marianita", "ninaPerreadora", (short)21, 5));

    }

    public void RecorrerLista(){
        for(int i=0;i<usuariosLista.size();i++){
            Usuario usuario = usuariosLista.get(i);
            Log.i( "Usuario"+i, "Nombre: "+usuario.getName()+", edad: "+usuario.getEdad());
        }
    }

    public void AgregarAdministrador() {
        Administrador admin = new Administrador("Ramón", "hat", (short) 20, 28);
        String correo = null;
        admin.AgregarCorreo(correo);
        admin.AgregarCodigoPostal(32);
    }
    /*public class Main Activity
    *         extends AppActivity (agegar herencia) (no exite la herencia múltiple, sólo simple.
    *         implements AppActivityInterferace, AppActivityInterferace2,... (en este caso sí pueden existir interfaces múltiples.
    *                                                                         es sólo la parte del diseño la que se muestra)
    */

}
