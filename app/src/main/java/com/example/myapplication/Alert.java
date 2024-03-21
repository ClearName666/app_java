package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;


public class Alert {
    public static void CreateAlertDialogOK(Context context, String HText,String Text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        Log.d("Dialog", "OK");
        builder.setTitle(HText);
        builder.setMessage(Text);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
