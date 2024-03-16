package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

// главаная страничка приложения
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

        RecyclerView recyclerView = findViewById(R.id.recyclerViewQrCode);
        RecyclerAddLines.ViewDatadaseQRCodes(recyclerView, this);

        // кнопка для перехода на следующию стрничку
        buttonToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                DataBases.saveToTextInputDataBase(inputText, MainActivity.this, ConstsSettings.MainInputTextIndexDataBaseText);
            }
        });

        // кнопка для добавления qr кода в базу данных переходит в приложение Binary Eye
        buttonAddQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    startActivityForResult(intent, SCAN_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    try {
                        Intent intentMarket = new Intent(Intent.ACTION_VIEW);
                        intentMarket.setData(Uri.parse("market://details?id=de.markusfisch.android.binaryeye"));
                        startActivity(intentMarket);
                    } catch (ActivityNotFoundException ex) {
                        Intent intentBrowser = new Intent(Intent.ACTION_VIEW);
                        intentBrowser.setData(Uri.parse("https://play.google.com/store/apps/details?id=de.markusfisch.android.binaryeye"));
                        startActivity(intentBrowser);
                    }
                }

            }
        });

        // кнопка для очищения всех записей в базе данных qr кодов
        buttonDelAllQRCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseQRCode databaseQRCode = new DatabaseQRCode(MainActivity.this);
                databaseQRCode.delAllQRCodes();
                RecyclerView recyclerView = findViewById(R.id.recyclerViewQrCode);
                RecyclerAddLines.delAllRecycler(recyclerView, MainActivity.this);
            }
        });
    }

    // метод для перехода в стороннее приложение
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("SCAN_RESULT");
                DatabaseQRCode codes = new DatabaseQRCode(this);
                codes.addText(result);
                codes.close();

                RecyclerView recyclerView = findViewById(R.id.recyclerViewQrCode);
                RecyclerAddLines.ViewDatadaseQRCodes(recyclerView, MainActivity.this);

            } else if (resultCode == RESULT_CANCELED) {
                Log.d("ScanResult", "Сканирование было отменено");
            }
        }
    }
}