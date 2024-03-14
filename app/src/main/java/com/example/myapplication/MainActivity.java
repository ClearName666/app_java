package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonToFirst = findViewById(R.id.buttonToFirst);
        TextInputEditText inputText = findViewById(R.id.inputText);

        DatabaseTextInput db = new DatabaseTextInput(this);
        String text = db.getText(ConstsSettings.MainInputTextIndexDataBaseText);
        if (text != null) {
            inputText.setText(text);
        }

        buttonToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                DataBases.saveToTextInputDataBase(inputText, MainActivity.this, ConstsSettings.MainInputTextIndexDataBaseText);
            }
        });
    }

}