package com.example.bluetoothapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothString";

    private static final String appName = "MyApp";

    private static final UUID MY_UUID_INSECURE = UUID.fromString("2d8822d4-8ccb-11ea-bc55-0242ac130003");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mContext = context;
        start();
    }

    private class AcceptThread extends Thread{
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            try {
                tmp=mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,MY_UUID_INSECURE);
                Log.d(TAG,"AcceptThread: Configurando servidor usando: "+MY_UUID_INSECURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmServerSocket=tmp;
        }
        public void run(){
            Log.d(TAG,"run: AcceptThread Ejecutando");
            BluetoothSocket socket=null;

            try {
                Log.d(TAG,"run: RFCOM server socket iniciando......");

                socket = mmServerSocket.accept();
                Log.d(TAG,"run: RFCOM server socket aceptó la conexión.");

            } catch (IOException e) {
                Log.e(TAG,"AcceptThread: IOException: "+e.getMessage());
            }
            if(socket!=null){
                connected(socket,mmDevice);
            }
            Log.i(TAG,"END mAcceptThread");
        }

        public void cancel(){
            Log.d(TAG,"cancel: Cancelando AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG,"cancel: Cerrado de AcceptThread ServeSocket fracasó. "+e.getMessage());
            }
        }
    }

    private class ConnectThread extends Thread{
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid){
            Log.d(TAG,"ConnectThread: iniciado.");
            mmDevice=device;
            deviceUUID=uuid;
        }

        public void run(){
            BluetoothSocket tmp=null;
            Log.i(TAG,"RUN mConnectThread");

            try {
                Log.d(TAG,"ConnectThread: Tratando de crear InsecureRFcommSocket usando UUID: "+MY_UUID_INSECURE);
                tmp=mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(TAG,"ConnectThread: no se pudo crear InsecureRFcommSocket "+e.getMessage());
            }

            mmSocket=tmp;
            mBluetoothAdapter.cancelDiscovery();
            try {
                //Conexión exitosa o sino una excepción
                mmSocket.connect();

                Log.d(TAG,"run: ConnectThread conectado");
            } catch (IOException e) {
                //Se cierra el Socket
                try {
                    mmSocket.close();
                    Log.d(TAG,"run: Socket cerrado");
                } catch (IOException ex) {
                    Log.e(TAG,"mConnectThread: run: No se pudo cerrar la conexión en el socket "+ex.getMessage());
                }
                Log.d(TAG,"run: ConnectThread: No se pudo conectar a UUID: "+MY_UUID_INSECURE);
            }

            connected(mmSocket,mmDevice);
        }

        public void cancel(){
            try {
                Log.d(TAG,"cancel: cerrando socket del cliente");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG,"cancel: close() de mmSocket en ConnectThread falló. "+e.getMessage());
            }
        }
    }

    public synchronized void start(){
        Log.d(TAG,"start");

        if (mConnectThread!=null){
            mConnectThread.cancel();
            mConnectThread=null;
        }
        if(mInsecureAcceptThread==null){
            mInsecureAcceptThread=new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    public void startClient(BluetoothDevice device, UUID uuid){
        Log.d(TAG,"startClient: Inicado.");

        mProgressDialog=ProgressDialog.show(mContext,"Connecting Bluetooth", "Por favor espere....",true);
        mConnectThread=new ConnectThread(device,uuid);
        mConnectThread.start();
    }

    private class ConnectedThread extends  Thread{
        private final BluetoothSocket mmSocket;
        private InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket){
            Log.d(TAG,"ConnectedThread: Iniciando.");

            mmSocket=socket;
            InputStream tmpIn=null;
            OutputStream tmpOut=null;

            try{
                mProgressDialog.dismiss();
            }catch(NullPointerException e){
                e.printStackTrace();
            }

            try {
                tmpIn=mmSocket.getInputStream();
                tmpOut=mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInStream=tmpIn;
            mmOutStream=tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];

            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                } catch (IOException e) {
                    Log.e(TAG,"write: error al leer la entrada del flujo. "+e.getMessage());
                    break;
                }
            }
        }

        public void write(byte[] bytes){
            String text=new String(bytes, Charset.defaultCharset());
            Log.d(TAG,"write: Escribiendo a la salida del flujo: "+text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG,"write: Error escribiendo en la salida. "+e.getMessage());
            }
        }

        public void cancel(){
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG,"conectado; Iniciando.");

        mConnectedThread=new ConnectedThread(mmSocket);
        mConnectedThread.start();

    }

    public void write(byte[] out){

        ConnectedThread r;
        Log.d(TAG,"write: Se llamó a escribir.");
        mConnectedThread.write(out);
    }
}
