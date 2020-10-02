package com.trishasofttech.speechapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToVoiceActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
Button btn_speak;
EditText et_msg;
TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_voice);
        et_msg = findViewById(R.id.et_msg);
        btn_speak = findViewById(R.id.btn_speak);
        textToSpeech = new TextToSpeech(TextToVoiceActivity.this, this);
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.speak(et_msg.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    @Override
    public void onInit(int i) {

    }
}