package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

// 1-ое окошко для перехода
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button buttonToSecond = findViewById(R.id.buttonToSecond);
        TextInputEditText inputText = findViewById(R.id.inputTextSecond);

        DatabaseTextInput db = new DatabaseTextInput(this);
        String text = db.getText(ConstsSettings.FirstInputTextIndexDataBaseText);
        if (text != null) {
            inputText.setText(text);
        }

        // кнопка для перехода на слудующию страничку
        buttonToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
                DataBases.saveToTextInputDataBase(inputText, FirstActivity.this, ConstsSettings.FirstInputTextIndexDataBaseText);
            }
        });
    }
}