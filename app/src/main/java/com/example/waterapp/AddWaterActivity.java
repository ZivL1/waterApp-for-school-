package com.example.waterapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddWaterActivity extends AppCompatActivity {
    SharedPreferences sp;
    TextView tHowToAddWater;
    Button bAddMl, bAddBottle, bSureAddMl, bCancleMl, bReturn;
    Dialog addMl;
    SeekBar ml;
    int waterToAdd = 0;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        double dWaterToAdd = data.getDoubleExtra("waterAdded",0);
                        double currentWater = Double.parseDouble(sp.getString("waterCount", "0"));
                        double newWaterAmount = currentWater + dWaterToAdd/1000;
                        Intent intent = new Intent();
                        intent.putExtra("waterAdded",newWaterAmount);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_water);
        sp = getSharedPreferences("details",0);
        tHowToAddWater = findViewById(R.id.tHowToAddWater);
        if(sp.getString("gender","male").equals("other")) {
            tHowToAddWater.setText("איך תרצו להוסיף מים?");
        }else if(sp.getString("gender","male").equals("female"))
        {
            tHowToAddWater.setText("איך תרצי להוסיף מים?");
        }

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initviews();
        bAddMl = findViewById(R.id.bAddMl);
        bAddBottle = findViewById(R.id.bAddBottle);
        bAddMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMlDialog();
            }
        });
        bReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bAddBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWaterActivity.this, ChooseBottleActivity.class);
                launcher.launch(intent);
            }
        });
    }

    private void initviews() {
        bAddMl = findViewById(R.id.bAddMl);
        bAddBottle = findViewById(R.id.bAddBottle);
        bReturn = findViewById(R.id.bReturn);
    }

    private void createMlDialog(){
        addMl = new Dialog(this);
        addMl.setContentView(R.layout.add_ml_dialog);
        addMl.setTitle("add Ml");
        addMl.setCancelable(false);
        ml = addMl.findViewById(R.id.sMl);
        ml.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterToAdd = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(AddWaterActivity.this, "water to add: " + waterToAdd + "ml", Toast.LENGTH_SHORT).show();
            }
        });
        bSureAddMl = addMl.findViewById(R.id.bSureAddMl);
        bCancleMl = addMl.findViewById(R.id.bCancleMl);
        bSureAddMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double currentWater = Double.parseDouble(sp.getString("waterCount", "0"));
                double newWaterAmount = currentWater + (double)waterToAdd/1000;
                Intent intent = new Intent();
                intent.putExtra("waterAdded",newWaterAmount);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        bCancleMl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMl.dismiss();
            }
        });
        addMl.show();
    }
}