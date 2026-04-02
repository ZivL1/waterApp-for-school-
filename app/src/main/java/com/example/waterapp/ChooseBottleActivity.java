package com.example.waterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseBottleActivity extends AppCompatActivity {
    SharedPreferences sp;
    LinearLayout main, linearLayoutHorizontal;
    HorizontalScrollView HorizontalScrollView;
    ImageView iv500, iv1000, iv755,iv1500;
    Button bReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_bottle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lChooseBottle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
    }

    private void initViews() {
        sp = getSharedPreferences("details",0);
        main = findViewById(R.id.lChooseBottle);
        main.setOrientation(LinearLayout.VERTICAL);
        HorizontalScrollView = new HorizontalScrollView(this);
        LinearLayout.LayoutParams hLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        HorizontalScrollView.setLayoutParams(hLayoutParams);
        linearLayoutHorizontal = new LinearLayout(this);
        linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutHorizontal.setLayoutParams(hLayoutParams);
        loadpics();
        bReturn = findViewById(R.id.bReturn2);
        initbuttons();
    }

    private void loadpics() {
        iv500= new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,500);
        layoutParams.setMargins(300,500,0,0);
        iv500.setLayoutParams(layoutParams);
        iv500.setImageResource(getResources().getIdentifier("d500ml","drawable",getPackageName()));
        linearLayoutHorizontal.addView(iv500);

        iv755= new ImageView(this);
        iv755.setLayoutParams(layoutParams);
        iv755.setImageResource(getResources().getIdentifier("d755ml","drawable",getPackageName()));
        linearLayoutHorizontal.addView(iv755);

        iv1000= new ImageView(this);
        iv1000.setLayoutParams(layoutParams);
        iv1000.setImageResource(getResources().getIdentifier("d1000ml","drawable",getPackageName()));
        linearLayoutHorizontal.addView(iv1000);

        iv1500= new ImageView(this);
        iv1500.setLayoutParams(layoutParams);
        iv1500.setImageResource(getResources().getIdentifier("d1500ml","drawable",getPackageName()));
        linearLayoutHorizontal.addView(iv1500);

        HorizontalScrollView.addView(linearLayoutHorizontal);
        main.addView(HorizontalScrollView,1);
    }

    private void initbuttons() {
        iv500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("waterAdded",(double)500);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        iv755.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("waterAdded",(double)755);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        iv1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("waterAdded",(double)1000);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        iv1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("waterAdded",(double)1500);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}