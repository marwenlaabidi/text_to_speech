package com.example.text_to_speech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS_french,mTTS_english;
    private EditText mEditText;
    RadioButton idfrench,idenglish;
    private Button mButtonSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonSpeak = findViewById(R.id.button_speak);
        mTTS_english = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTTS_english.setLanguage(Locale.ENGLISH);

                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                } else {
                    mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed");
            }
        });
        mTTS_french = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTTS_french.setLanguage(Locale.FRENCH);

                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                } else {
                    mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed");
            }
        });

        mEditText = findViewById(R.id.edit_text);
        idfrench = (RadioButton) findViewById(R.id.idfrench);
        idenglish = (RadioButton) findViewById(R.id.idenglish);

        mButtonSpeak.setEnabled(true);
        mButtonSpeak.setOnClickListener(view -> {

            if(idfrench.isChecked())
            {
                mButtonSpeak.setEnabled(true);
                String text = mEditText.getText().toString();

                mTTS_french.speak(text, TextToSpeech.QUEUE_FLUSH, null);

            }else if(idenglish.isChecked())
            {
                mButtonSpeak.setEnabled(true);

                String text = mEditText.getText().toString();

                mTTS_english.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Alert");
                dialog.setMessage("veuillez choisir une langue");
                dialog.setPositiveButton("ok", (dialog1, id) -> mButtonSpeak.setEnabled(false));
                dialog.show();}

        });
    }

    @Override
    protected void onDestroy() {
        if (mTTS_french != null) {
            mTTS_french.stop();
            mTTS_french.shutdown();
        }
        if (mTTS_english != null) {
            mTTS_english.stop();
            mTTS_english.shutdown();
        }

        super.onDestroy();
    }
}