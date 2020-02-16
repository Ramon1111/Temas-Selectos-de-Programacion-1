package com.example.tareadebug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int valor = 10;
        for(int i=1;i<=20;i++){
            try{
                valor=10/0;
                Log.i("mensaje", "sí pudo");
            }
            catch(Exception e){
                Log.e("mensaje", i+": Se debe cerrar la aplicación en 20 segundos.");
            }
        }
    }
}
