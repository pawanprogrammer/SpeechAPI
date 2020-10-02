package com.trishasofttech.speechapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceToTextActivity extends AppCompatActivity {
Button btn_speak;
TextView  tv_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_msg = findViewById(R.id.tv_msg);
        btn_speak = findViewById(R.id.btn_speak);

        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*RecognizerIntent will use to recognize the speech*/
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                /*to call all support language from mobile*/
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                /*to access the user mobile default language*/
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                /*to display for speak*/
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
                /*to request the voice recognize with all functions to OS*/
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== RESULT_OK && data!=null)
        {
            /*to pass the voice msg result into arraylist as string type*/
            ArrayList<String> arrayList  = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            /*to display the voice msg converted to text form*/
            tv_msg.setText(arrayList.get(0));

            /*To share the recorded voice msg to social media apps in text form*/
            /*to send the data to other apps using intent*/
            /*Intent share = new Intent(Intent.ACTION_SEND);
            *//*to attach the msg with intent object*//*
            share.putExtra(Intent.EXTRA_TEXT, arrayList.get(0));
            *//*to define the data type*//*
            share.setType("text/plain");
            *//*to start the intent*//*
            startActivity(Intent.createChooser(share, "Share Via:"));*/

            /*to make voice automation type function like open camera, call Viru sir*/
            if (arrayList.get(0).equalsIgnoreCase("open camera"))
            {
                /*to open the mobile camera using intent function*/
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);
            }
            else if (arrayList.get(0).equalsIgnoreCase("call Veeru"))
            {
                callphone();
            }

        }
    }

    private void callphone() {
        /*if user mobile version is greater than or equal to marsmallow*/
        /*and your app contain runtime time permission*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
        checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2);
        }
        else {
            /*without runtime permission*/
            Intent call = new Intent(Intent.ACTION_CALL);
            /*to attach the mobile no*/
            call.setData(Uri.parse("tel:9116961419"));
            startActivity(call);
        }
    }

    /*to get the response for runtime permission*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2 )
        {
            /*user allow the runtime permission*/
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                /*call to viru sir*/
                callphone();
            }
            else {
                Toast.makeText(this, "Permission Deny by User", Toast.LENGTH_SHORT).show();
            }
        }
    }
}