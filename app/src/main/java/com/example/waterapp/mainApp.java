package com.example.waterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mainApp extends AppCompatActivity {
    TextView tHello,tHowMuchWater,tWaterCount;
    SharedPreferences sp;
    Button bAddWater;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        int waterAdded = data.getIntExtra("waterAdded", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("waterCount", String.valueOf(waterAdded));
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_app);
        initviews();
        tHello.setText("שלום "+ sp.getString("name","אורח"));
        if(sp.getString("gender","male").equals("other")) {
            tHowMuchWater.setText("כמה מים ששתיתם היום:");
            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"3.7L");
        }else if(sp.getString("gender","male").equals("female"))
        {
            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"2.7L");
        }else{
            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"3.7L");
        }



        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bAddWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(mainApp.this, AddWaterActivity.class);
            launcher.launch(intent);
            }
        });
    }

    private void initviews() {
        tHello = findViewById(R.id.tHello);
        tHowMuchWater = findViewById(R.id.tHowMuchWater);
        tWaterCount = findViewById(R.id.tWaterCount);
        sp = getSharedPreferences("details",0);
        bAddWater = findViewById(R.id.bAddWater);
    }
}