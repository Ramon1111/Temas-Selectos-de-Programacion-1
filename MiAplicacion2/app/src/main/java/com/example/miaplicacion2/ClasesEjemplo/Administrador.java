package com.example.miaplicacion2.ClasesEjemplo;

import android.util.Log;

public class Administrador extends Usuario implements DatosContacto{
    //Para heredar se tiene que crear forsozamente un constructor
    public Administrador(String name, String password, short edad, int id) {
        super(name, password, edad, id);

    }

    @Override //Polimorfismo porque sobrecargamos el método (?) jeje
    public void AgregarCorreo(String correo) {
        try{
            Log.i("Correo","El correo del usuario es: "+correo);
        }
        catch (NullPointerException e){
            Log.e("Error de correo","El correo ingresado no es correcto");
        }
    }

    @Override
    public void AgregarCodigoPostal(int cpostal) {
        try{
            int Postal=cpostal/0;
            Log.i("CPostal","El cpostal del usuario es: "+Postal);
        }
        catch (NumberFormatException e){
            Log.e("Error de cpostal","Error al ingresar el codigo postal");
        }
        catch (Exception e){
            Log.e("Error","Error al ingresar el codigo postal");
        }
    }
}
