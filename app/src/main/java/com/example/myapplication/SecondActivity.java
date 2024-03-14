package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button buttonToThird = findViewById(R.id.buttonToThird);
        TextInputEditText inputText = findViewById(R.id.inputTextSecond);

        DatabaseTextInput db = new DatabaseTextInput(this);
        String text = db.getText(ConstsSettings.SecondInputTextIndexDataBaseText);
        if (text != null) {
            inputText.setText(text);
        }
        buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                DataBases.saveToTextInputDataBase(inputText, SecondActivity.this, ConstsSettings.SecondInputTextIndexDataBaseText);
            }
        });
    }
}