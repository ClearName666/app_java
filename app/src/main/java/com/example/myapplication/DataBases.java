package com.example.myapplication;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;

public class DataBases {
    public static void saveToTextInputDataBase(TextInputEditText inputText, Context thisClass, int indexInputText) {
        String str = inputText.getText().toString();
        DatabaseTextInput db = new DatabaseTextInput((thisClass));
        db.addText(indexInputText, str);
    }
    
}
