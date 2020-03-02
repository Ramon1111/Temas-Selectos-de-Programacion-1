package com.example.metdidactico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText edtNombre, edtCorreo, edtPassword;
    Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se ejecuta al incio.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Los recursos se cuguardan en un archivo R por id que es un dato nimérico
        edtNombre=findViewById(R.id.edtNombreMain);
        edtCorreo=findViewById(R.id.edtCorreoMain);
        edtPassword=findViewById(R.id.edtContrasenaMain);
        btnCrear=findViewById(R.id.btnCrearMain);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre=edtNombre.getText().toString();
                String Correo=edtCorreo.getText().toString();
                String Password=edtPassword.getText().toString();
                if(Nombre.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Escriba su nombre", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Correo.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Escriba un correo", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Password.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Escriba una contraseña", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();

                            //Para mandar a la siguiente página
                            //También se usa para pasar parámetros por lo que se usa un método
                            Intent intentlogin=new Intent(getApplicationContext(), LoginActivity.class);
                            intentlogin.putExtra(getString(R.string.mainStringHintCorreo),Correo);
                            intentlogin.putExtra(getString(R.string.mainStringHintContrasena),Password);
                            startActivity(intentlogin);
                            finish(); //Finaliza la tarea (la destruye)
                        }
                    }
                }
            }
        });
    }
}