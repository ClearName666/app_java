package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAddLines {
    public static void addRecycler(List<View> listLines, RecyclerView recyclerViewID, Context context) {
        recyclerViewID.setLayoutManager(new LinearLayoutManager(context));
        AdapterForItemsRecycler adapter = new AdapterForItemsRecycler(listLines);
        recyclerViewID.setAdapter(adapter);
    }
    public static void delAllRecycler(RecyclerView recyclerViewID, Context context) {
        List<View> listLines = new ArrayList<>();
        TextView textView = new TextView(context);
        textView.setText("Для добавления qr кода нажмите кнопку ниже!");
        listLines.add(textView);
        recyclerViewID.setLayoutManager(new LinearLayoutManager(context));
        AdapterForItemsRecycler adapter = new AdapterForItemsRecycler(listLines);
        recyclerViewID.setAdapter(adapter);
    }
    public static void ViewDatadaseQRCodes(RecyclerView recyclerViewID, Context context) {
        DatabaseQRCode codes = new DatabaseQRCode(context);
        List<View> listCodesText = codes.getAllQRCodes(context);
        addRecycler(listCodesText, recyclerViewID, context);
        codes.close();
    }
}
