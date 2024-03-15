package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_REQUEST_CODE = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonToFirst = findViewById(R.id.buttonToFirst);
        Button buttonAddQRCode = findViewById(R.id.buttonAddQRCode);
        Button buttonDelAllQRCodes = findViewById(R.id.buttonDelAllQRCode);

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
        buttonAddQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(intent, SCAN_REQUEST_CODE);
            }
        });
        buttonDelAllQRCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseQRCode databaseQRCode = new DatabaseQRCode(MainActivity.this);
                databaseQRCode.delAllQRCodes();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("SCAN_RESULT");

                //RecyclerView recyclerViewQrCode = findViewById(R.id.recyclerViewQrCode);
                DatabaseQRCode codes = new DatabaseQRCode(this);
                codes.addText(result);
                Log.d("ScanResult", "Отсканированный текст: " + result);
                ArrayList<String> qrCodeDB = codes.getAllQRCodes();
                for (String item : qrCodeDB) {
                    Log.d("ScanResult", item);
                }


            } else if (resultCode == RESULT_CANCELED) {
                Log.d("ScanResult", "Сканирование было отменено");
            }
        }
    }
}