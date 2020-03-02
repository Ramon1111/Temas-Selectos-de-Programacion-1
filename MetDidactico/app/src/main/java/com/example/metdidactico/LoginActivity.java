package com.example.metdidactico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnIngresar;
    Button btnCrearCuenta;

    //Varables para los datos del login
    String correo;
    String password;

    EditText edtCorreo;
    EditText edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//Esto es para asignar la vista completa a la pantalla del celular

        btnIngresar=(Button)findViewById(R.id.btnIngresarLogin);
        btnCrearCuenta=(Button)findViewById(R.id.btnCrearLogin);
        edtCorreo=findViewById(R.id.edtCorreoLogin);
        edtPassword=findViewById(R.id.edtContrasenaLogin);

        //Para obtener las variables del logeo se usa el objeto Bundle
        try {
            Bundle bundleRegistro = getIntent().getExtras();
            correo = bundleRegistro.getString(getString(R.string.mainStringHintCorreo));
            password = bundleRegistro.getString(getString(R.string.mainStringHintContrasena));
        }
        catch(NullPointerException e) {
            Toast.makeText(this, "Necesitas crear una cuenta", Toast.LENGTH_SHORT).show();
        }

        btnIngresar.setOnClickListener(onClickIngresar);
        btnCrearCuenta.setOnClickListener(onClickCrearCuenta);


        /*btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCorreo.getText().toString()==correo&&edtPassword.getText().toString()==password)
                    Toast.makeText(getApplicationContext(), "Datos correctos", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Datos Incorrectos",Toast.LENGTH_SHORT);
            }
        });*/
    }

    View.OnClickListener onClickCrearCuenta=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentSignUp=new Intent(getApplicationContext(), RegistroActivity.class);
            startActivity(intentSignUp);
            finish();
            //Usualmente no se sugiere que siga corriendo la activity pero hay veces que sí xd.
        }
    };

    //Otra forma de crear elevento de crear cuenta
    View.OnClickListener onClickIngresar=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(edtCorreo.getText().toString().equals(correo)&&edtPassword.getText().toString().equals(password))
                Toast.makeText(getApplicationContext(), "Bienvenido, has ingresado sesión", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Correo o contraseña inválidos",Toast.LENGTH_SHORT).show();
        }
    };

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
