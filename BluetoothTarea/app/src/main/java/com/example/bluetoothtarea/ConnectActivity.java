package com.example.bluetoothtarea;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectActivity extends AppCompatActivity {

    String TAG = "MenjsajeError";
    String address;

    //Objetos para Bluetooth
    private BluetoothAdapter adaptadorBT;
    private BluetoothSocket socketBT=null;

    //No entiendo por qué con otros uuid no pudo trabajar :(
    //Intenté con el generador de UUIDs en https://www.uuidgenerator.net/ pero me aceptó esta.
    private UUID uuidBT=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //Elementos
    EditText edtTexto;
    Button btnClear, btnSend;

    //Objeto de clase ConnectThread -> genera el hilo para envío de información
    ConnectedThreadBT connectedThreaBT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        //Inicializar adaptador
        adaptadorBT=BluetoothAdapter.getDefaultAdapter();

        edtTexto=findViewById(R.id.edtText);

        btnSend=findViewById(R.id.btnSend);
        btnClear=findViewById(R.id.btnClear);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarCadena();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarPantalla();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundleExtras=getIntent().getExtras();
        address=bundleExtras.getString("BTAddress");
        BluetoothDevice dispositivo = adaptadorBT.getRemoteDevice(address);

        try{
            socketBT=dispositivo.createInsecureRfcommSocketToServiceRecord(uuidBT);
        } catch (IOException e) {
            Log.e(TAG,"Error en CreateRFcommSocket "+e.getMessage());
        }

        try {
            socketBT.connect();
        } catch (IOException e) {
            Log.e(TAG,"Error en Connect del socket"+e.getMessage());

            Toast.makeText(getApplicationContext(),"Error al conectar con dispositivo" ,Toast.LENGTH_LONG).show();

            try {
                socketBT.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        connectedThreaBT=new ConnectedThreadBT(socketBT);
        connectedThreaBT.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            socketBT.close();
        } catch (IOException e) {
            Log.e(TAG,"Error en cerrar socket "+e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socketBT.close();
        } catch (IOException e) {
            Log.e(TAG,"Error en cerrar socket "+e.getMessage());
        }
    }

    //Método para limpioar pantalla
    private void limpiarPantalla() {
        connectedThreaBT.write("C");
    }

    //Mpetodo para enviar cadena del texto
    private void EnviarCadena() {
        connectedThreaBT.write("T"+edtTexto.getText().toString());
    }

    private class ConnectedThreadBT extends Thread{
        private OutputStream mmOutputStream;

        private ConnectedThreadBT(BluetoothSocket socket){
            try{
                this.mmOutputStream=socket.getOutputStream();
            }catch(IOException e){
                Log.e(TAG,"Error OutputStream "+e.getMessage());
            }
        }

        public void write(String texto){
            try{
                mmOutputStream.write(texto.getBytes());
            }catch(IOException e){
                Log.e(TAG,"Error Write "+e.getMessage());
            }
        }
    }


}
