package com.example.tomafoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CODE_ASK_PERMISION = 3;
    ImageButton imgCamera;
    ImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFoto = findViewById(R.id.imgFoto);
        imgCamera = findViewById(R.id.imgCamara);

        imgFoto.setOnClickListener(imgFotoClick);

        imgCamera.setOnClickListener(onClickCamara);
    }

    View.OnClickListener onClickCamara = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            invocaCamara();
        }
    };

    View.OnClickListener imgFotoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, imgFoto);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(itemCliclListener);
            popupMenu.show();
        }
    };

    PopupMenu.OnMenuItemClickListener itemCliclListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.popmGuarda:
                    guardaFoto();
                    break;
                case R.id.popmTomaFoto:
                    break;
            }
            return true;
        }
    };

    private void guardaFoto() {
        pedirPermisos(); //Es recomendable pedir desde el inicio las cosas

        File imgFile = getOutputMediaFile();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imgFoto.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray=stream.toByteArray();

        try{
            FileOutputStream fos = new FileOutputStream(imgFile);
            fos.write(byteArray);
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"El archivo fue almacenado en: "+imgFile.getPath().toString(), Toast.LENGTH_SHORT).show();
    }

    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }


    //Este método sólo le da el formato al archivo
    private static File getOutputMediaFile() {
        File mediaStorageDir=new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "TomaFotoApp");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.d("ErrorTMAP","No se pudo crear el directorio");
                return null;
            }
        }


        //Se van a crear las propiedades
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile=new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
        return mediaFile;
        /*Cuidado aqui, checar en github*/
    }

    //Para pedir los permisos jijiji
    private void pedirPermisos() {
        int permisoStorage = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisoCamara = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        int permisoStorageRead = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permisoStorage!=PackageManager.PERMISSION_GRANTED && permisoCamara!=PackageManager.PERMISSION_GRANTED && permisoStorageRead!=PackageManager.PERMISSION_GRANTED){
            //Esto sirve para preguntar por los permisos. creo sólo es necesario el write external
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISION);
            }
        }

    }

    private void invocaCamara() {
        Intent tomarImagenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tomarImagenIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(tomarImagenIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle---> Pasar datos
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imgBitmap);
        }
    }
}
