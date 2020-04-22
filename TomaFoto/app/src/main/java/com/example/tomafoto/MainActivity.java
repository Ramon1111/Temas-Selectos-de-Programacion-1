package com.example.tomafoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageButton imgCamera;
    ImageView imgFoto;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgCamera=findViewById(R.id.imgCamara);
        imgFoto=findViewById(R.id.imgFoto);

        imgCamera.setOnClickListener(onClickCamara);
    }

    View.OnClickListener onClickCamara = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            invocaCamara();
        }
    };

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
