package com.example.metdidactico;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//Esto es para asignar la vista completa a la pantallña del celular
    }

    /******************* Checar ciclo de vida de las aplicaciones móviles ************************/
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"Método onStart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Método onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Método onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"Método onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Método onDestroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"Método onRestart",Toast.LENGTH_SHORT).show();
    }
}
