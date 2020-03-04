package com.example.metdidactico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.metdidactico.Fragments.BuscarFragment;
import com.example.metdidactico.Fragments.CarritoFragment;
import com.example.metdidactico.Fragments.InicioFragment;
import com.example.metdidactico.Fragments.UsuarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {

    //ClipData.Item Inicio, Buscar, Lupa;
    BottomNavigationView btnNavPrincipal;
    Fragment fragmentSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNavPrincipal=findViewById(R.id.btnNavPrincipal);
        fragmentSeleccionado=new InicioFragment(); //Se le pone el fragment correspondiente a la entrada
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContentMain, fragmentSeleccionado).commit();  //Remplaza el fragment selecdcionado por el que queremos.

        btnNavPrincipal.setOnNavigationItemSelectedListener(OnNavMenuSelect);
    }

    BottomNavigationView.OnNavigationItemSelectedListener OnNavMenuSelect = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_nav_home:
                    fragmentSeleccionado=new InicioFragment();
                    break;
                case R.id.menu_nav_search:
                    fragmentSeleccionado=new BuscarFragment();
                    break;
                case R.id.menu_nav_shop:
                    fragmentSeleccionado=new CarritoFragment();
                    break;
                case R.id.menu_nav_user:
                    fragmentSeleccionado=new UsuarioFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragContentMain, fragmentSeleccionado).commit();
            return true;
        }
    };

}
