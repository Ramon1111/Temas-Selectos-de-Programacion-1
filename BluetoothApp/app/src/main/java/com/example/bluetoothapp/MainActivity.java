package com.example.bluetoothapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10;
    private Set<BluetoothDevice> DispEmparejados;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothA2dp audifonosDP;

    BluetoothConnectionService mBluetoothConnection;

    Button btnStatus;

    MediaPlayer mp;

    private AudioManager amanager = null;

    private BluetoothHeadset audifonos;
    private BluetoothDevice dispositivo;

    String TAG = "Etiqueta para errores y demás";

    ArrayList<String> nombreDispositivo;
    ArrayList<String> direccionDispositivo;
    private ListView lstDispositivos;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dispositivo = null;

        mBluetoothConnection = new BluetoothConnectionService(MainActivity.this);

        lstDispositivos = findViewById(R.id.lstDispositivos);

        mp= MediaPlayer.create(this, R.raw.indeciso);

        btnStatus=findViewById(R.id.btnStatus);
        btnStatus.setOnClickListener(onClickEvent);



        Button btnEnviar = findViewById(R.id.btnSend);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                amanager.setMode(AudioManager.MODE_IN_CALL);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.start();


                Log.i("TAGFEO1",""+BluetoothDevice.ACTION_ACL_CONNECTED);
                Log.i("TAGFEO5",""+audifonos.isAudioConnected(dispositivo));
                Log.i("TAGFEO2",""+audifonos.getConnectionState(dispositivo));
            }
        });



        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(this, "No cuentas con Bluethood :c", Toast.LENGTH_SHORT).show();
        }else{
            if(!bluetoothAdapter.isDiscovering() /*Si el usuario lo tiene encendido*/){
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Llamamos de manera implícita
                startActivityForResult(enableIntent,REQUEST_ENABLE_BT); //Pide habilita el bluetoth si es que no está necendido
            }
        }

    }

    public void onDestroy() {
        mp.stop();
        amanager.setMode(AudioManager.MODE_NORMAL);
        amanager.setBluetoothScoOn(false);
        super.onDestroy();
    }

    View.OnClickListener onClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Sí entra",Toast.LENGTH_SHORT).show();

            Log.i("Direccion",""+dispositivo.getAddress());
            Log.i("Estado",""+audifonos.isAudioConnected(dispositivo));
        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            verificarDispositivos();

            bluetoothAdapter.getProfileProxy(this,profileListener,BluetoothProfile.HEADSET);

            lstDispositivos.setOnItemClickListener(ElementoSeleccionado);

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }



    BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if(profile==BluetoothProfile.HEADSET){
                audifonos=(BluetoothHeadset) proxy;
            }
        }

        @Override
        public void onServiceDisconnected(int profile) {
            if(profile==BluetoothProfile.HEADSET){
                audifonos=null;
            }
        }
    };

    public void startConnection(){
        startBTConnection(dispositivo,BTMODULEUUID);
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG,"startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);

    }

    AdapterView.OnItemClickListener ElementoSeleccionado = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            dispositivo = bluetoothAdapter.getRemoteDevice(direccionDispositivo.get(position));
            startConnection();

        }
    };

    private void verificarDispositivos() {
        DispEmparejados=bluetoothAdapter.getBondedDevices();

        if(DispEmparejados.size()>0){

            nombreDispositivo=new ArrayList<>();
            direccionDispositivo=new ArrayList<>();

            for(BluetoothDevice dispositivoEmp : DispEmparejados){
                nombreDispositivo.add(dispositivoEmp.getName());
                direccionDispositivo.add(dispositivoEmp.getAddress());
            }

            ArrayAdapter<String>adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,nombreDispositivo);
            lstDispositivos.setAdapter(adapter);

        }else{
            Toast.makeText(this,"Ninguno emparejado",Toast.LENGTH_SHORT).show();
        }
    }


}