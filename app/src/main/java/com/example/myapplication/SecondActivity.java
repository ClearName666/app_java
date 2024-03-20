package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button buttonToThird = findViewById(R.id.buttonToThird);
        Button buttonToGoWeb = findViewById(R.id.buttonGoWedView);
        TextInputEditText inputText = findViewById(R.id.inputTextSecond);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

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
        buttonToGoWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                WebView webView = (WebView) findViewById(R.id.webview);

                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                TextInputEditText inputURLWeb = findViewById(R.id.inputURLWeb);
                webView.loadUrl(inputURLWeb.getText().toString());

                progressBar.setVisibility(View.GONE);
            }
        });


    }
}