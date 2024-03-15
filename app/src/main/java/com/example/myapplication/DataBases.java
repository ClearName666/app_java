package com.example.myapplication;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;

// класс с удобными методами для взяимодействия с базыми данных
public class DataBases {

    // метод для сохранения в в поле текста за счет базы даннх DatabaseTextInput
    public static void saveToTextInputDataBase(TextInputEditText inputText, Context thisClass, int indexInputText) {
        String str = inputText.getText().toString();
        DatabaseTextInput db = new DatabaseTextInput((thisClass));
        db.addText(indexInputText, str);
    }
    
}
