package com.example.waterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.appcompat.widget.Toolbar;

public class mainApp extends AppCompatActivity {
    TextView tHello,tHowMuchWater,tWaterCount,tWaterCountYesterday,tHowMuchWaterYesterday;
    SharedPreferences sp;
    Button bAddWater;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        double waterAdded = data.getDoubleExtra("waterAdded", 0.0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("waterCount", String.valueOf(waterAdded));
                        editor.commit();
                        if(sp.getString("gender","male").equals("other")) {
                            tHowMuchWater.setText("כמה מים ששתיתם היום:");
                            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"3.7L");
                        }else if(sp.getString("gender","male").equals("female"))
                        {
                            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"2.7L");
                        }else{
                            tWaterCount.setText(sp.getString("waterCount","0") + "/"+"3.7L");
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_app);
        initviews();
        checkDailyReset();
        setTexts();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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

    private void setTexts() {
        String gender = sp.getString("gender","male");
        String waterCount = sp.getString("waterCount","0.0");
        String lastWaterCount = sp.getString("lastWaterCount","0.0");
        tHello.setText("שלום "+ sp.getString("name","אורח"));
        if(gender.equals("other")) {
            tHowMuchWater.setText("כמה מים ששתיתם היום:");
            tHowMuchWaterYesterday.setText("כמות המים ששתיתם בפעם אחרונה:");
            tWaterCount.setText(waterCount + "/3.7L");
            tWaterCountYesterday.setText(lastWaterCount+"/3.7L");
        }else if(gender.equals("female"))
        {
            tWaterCount.setText(waterCount + "/2.7L");
            tWaterCountYesterday.setText(lastWaterCount+"/2.7L");
        }else{
            tWaterCount.setText(waterCount + "/3.7L");
            tWaterCountYesterday.setText(lastWaterCount+"/3.7L");
        }
    }

    private void initviews() {
        tHello = findViewById(R.id.tHello);
        tHowMuchWater = findViewById(R.id.tHowMuchWater);
        tHowMuchWaterYesterday = findViewById(R.id.tHowMuchWaterYesterday);
        tWaterCountYesterday = findViewById(R.id.tWaterCountYesterday);
        tWaterCount = findViewById(R.id.tWaterCount);
        sp = getSharedPreferences("details",0);
        bAddWater = findViewById(R.id.bAddWater);
    }
    private void checkDailyReset()
    {
        String lastDate = sp.getString("lastDate",null);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if(lastDate==null||!lastDate.equals(todayDate))
        {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("lastWaterCount", sp.getString("waterCount","0.0"));
            editor.putString("waterCount","0.0");
            editor.putString("lastDate",todayDate);
            editor.commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==R.id.action_changeDetails)
        {
            Intent intent = new Intent(this,ChangeDetails.class);
            startActivity(intent);
        }else if(id==R.id.action_mainApp)
        {
            Intent intent = new Intent(this, mainApp.class);
            startActivity(intent);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}