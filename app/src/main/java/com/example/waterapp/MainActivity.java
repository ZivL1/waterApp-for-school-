package com.example.waterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    RadioGroup chooseGender;
    RadioButton gender;
    EditText name;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        String checkName = sp.getString("name",null);
        if(checkName!=null)
        {
            Intent intent = new Intent(MainActivity.this, mainApp.class);
            startActivity(intent);
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = chooseGender.getCheckedRadioButtonId();
                String sName = name.getText().toString();
                gender = findViewById(radioId);
                if(radioId == -1 || sName.trim().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "השם או המגדר לא מולאו אנא מלא את הכל", Toast.LENGTH_LONG).show();
                }else{
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name",sName);
                String sGender = "";
                if(gender.getId() == R.id.rMale)
                {
                    sGender = "male";
                }else if(gender.getId()==R.id.rFemale)
                {
                    sGender = "female";
                }else
                {
                    sGender = "other";
                }
                editor.putString("gender",sGender);
                editor.putString("waterCount","0.0");
                editor.commit();
                }
                Intent intent = new Intent(MainActivity.this, mainApp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        btn = findViewById(R.id.bFinishStart);
        name = findViewById(R.id.etName);
        chooseGender = findViewById(R.id.rChooseGender);
        sp =getSharedPreferences("details",0);
    }
}