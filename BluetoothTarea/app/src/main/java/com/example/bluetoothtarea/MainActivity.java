package com.example.bluetoothtarea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 10;
    private Set<BluetoothDevice> DispEmparejados;
    private BluetoothAdapter bluetoothAdapter;

    private BluetoothDevice dispositivo;

    String TAG = "Etiqueta para errores y demás";

    Button btnConnect;

    ArrayList<String> nombreDispositivo;
    ArrayList<String> direccionDispositivo;
    private ListView lstDispositivos;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dispositivo = null;

        lstDispositivos = findViewById(R.id.lstDispositivos);

        btnConnect=findViewById(R.id.btnConectar);
        btnConnect.setOnClickListener(onClickEvent);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(this, "No cuentas con Bluethood :c", Toast.LENGTH_SHORT).show();
        }else{
            if(!bluetoothAdapter.isDiscovering()){   /*Si el usuario lo tiene encendido*/
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //Llamamos de manera implícita
                startActivityForResult(enableIntent,REQUEST_ENABLE_BT); //Pide habilita el bluetoth si es que no está necendido
            }
        }
    }

    View.OnClickListener onClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String auxiliarDireccion=dispositivo.getAddress();
            Intent connectBT = new Intent(getApplicationContext(),ConnectActivity.class);
            connectBT.putExtra("BTAddress",auxiliarDireccion);
            //Toast.makeText(getApplicationContext(),""+dispositivo.getAddress(),Toast.LENGTH_SHORT).show();
            startActivity(connectBT);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            verificarDispositivos();

            lstDispositivos.setOnItemClickListener(ElementoSeleccionado);

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    AdapterView.OnItemClickListener ElementoSeleccionado = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dispositivo = bluetoothAdapter.getRemoteDevice(direccionDispositivo.get(position));
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