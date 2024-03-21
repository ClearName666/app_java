package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 1-ое окошко для перехода
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button buttonToSecond = findViewById(R.id.buttonToSecond);
        Button buttonStartToURL = findViewById(R.id.buttonStartToURL);
        TextInputEditText inputText = findViewById(R.id.inputTextSecond);

        RadioButton postRadioButton = findViewById(R.id.radioButtonPost);
        RadioButton getRadioButton = findViewById(R.id.radioButtonGet);



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
        // смена надписей возле cwitch
        /*switchPOST_GET.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switchPOST_GET.setText("POST");
                else
                    switchPOST_GET.setText("GET");
            }
        });*/
        // обработка запросов GET POST
        buttonStartToURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText inputURL = findViewById(R.id.inputTextURLEditText);
                if (postRadioButton.isChecked()) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(() -> {
                        TextInputEditText inputPOSTParameters = findViewById(R.id.inputTextPOSTEditText);
                        String response = HttpConnection.CreatePOSTRequest(
                                inputURL.getText().toString(),
                                inputPOSTParameters.getText().toString()
                        );
                        runOnUiThread(() -> {
                            if (response.length() < 50) {
                                Alert.CreateAlertDialogOK(FirstActivity.this, "Ошибка", response);
                            }
                            TextView textViewEditTextHTTPResponse = findViewById(R.id.textViewEditTextHTTPResponse);
                            textViewEditTextHTTPResponse.setText(response);

                            ScrollView scrollViewLondHTTPResponse = findViewById(R.id.scrollViewLondHTTPResponse);
                            scrollViewLondHTTPResponse.requestLayout();
                        });

                    });
                }
                if (getRadioButton.isChecked()) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(() -> {
                        TextInputEditText inputGETParameters = findViewById(R.id.inputTextGETEditText);
                        String response = HttpConnection.CreateGetRequest(
                                inputURL.getText().toString(),
                                inputGETParameters.getText().toString()
                        );
                        runOnUiThread(() -> {
                            TextView textViewEditTextHTTPResponse = findViewById(R.id.textViewEditTextHTTPResponse);
                            textViewEditTextHTTPResponse.setText(response);

                            ScrollView scrollViewLondHTTPResponse = findViewById(R.id.scrollViewLondHTTPResponse);
                            scrollViewLondHTTPResponse.requestLayout();
                        });

                    });
                }
            }
        });


    }
}