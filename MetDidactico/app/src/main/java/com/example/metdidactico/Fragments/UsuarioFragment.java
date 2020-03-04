package com.example.metdidactico.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.metdidactico.LoginActivity;
import com.example.metdidactico.R;
import com.example.metdidactico.RegistroActivity;

public class UsuarioFragment extends Fragment {

    Button btnCrearUsuario;
    Button btnIngresarUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_usuario, container, false);

        btnIngresarUsuario=v.findViewById(R.id.btnIngresarFragUsuario);
        btnCrearUsuario=v.findViewById(R.id.btnCrearFragUsuario);

        btnIngresarUsuario.setOnClickListener(OnClickIngresar);
        btnCrearUsuario.setOnClickListener(OnClickCrear);

        return v;
    }

    View.OnClickListener OnClickIngresar=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intIngresar=new Intent(getActivity(), LoginActivity.class);
            startActivity(intIngresar);
            //getActivity().finish();
        }
    };

    View.OnClickListener OnClickCrear=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intCrear=new Intent(getActivity(), RegistroActivity.class);
            startActivity(intCrear);
            //getActivity().finish();
        }
    };
}
