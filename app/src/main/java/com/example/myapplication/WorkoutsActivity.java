package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

public class WorkoutsActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra("id", -1);
        setContent(id);
        init();
        buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }
    private boolean setContent(int id){
        switch (id){
            case 1: setContentView(R.layout.press1_activity); return true;
            case 2: setContentView(R.layout.press2_activity); return true;
            case 3: setContentView(R.layout.press3_activity); return true;
            case 4: setContentView(R.layout.hands1_activity); return true;
            case 5: setContentView(R.layout.hands2_activity); return true;
            case 6: setContentView(R.layout.hands3_activity); return true;
            case 7: setContentView(R.layout.breats1_activity); return true;
            case 8: setContentView(R.layout.breats2_activity); return true;
            case 9: setContentView(R.layout.breats3_activity); return true;
            case 10: setContentView(R.layout.legs1_activity); return true;
            case 11: setContentView(R.layout.legs2_activity); return true;
            case 12: setContentView(R.layout.legs3_activity); return true;
            case 13: setContentView(R.layout.back1_activity); return true;
            case 14: setContentView(R.layout.back2_activity); return true;
            case 15: setContentView(R.layout.back3_activity); return true;
            default: return false;
        }
    }
    private void init(){
        buttonBack = findViewById(R.id.btnBack);
    }
}