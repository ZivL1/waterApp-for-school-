package com.example.waterapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class ChangeDetails extends AppCompatActivity {
    SharedPreferences sp;
    RadioGroup rChangeChooseGender;
    RadioButton gender;
    EditText etChangeName;
    Dialog changeGender, changeName;
    Button bChangeGender, bChangeName, bFinishChangeGender, bCancleChangeGender, bCancleChangeName, bFinishChangeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initviews();
        bChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChangeNameDialog();
            }
        });
        bChangeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChangeGenderDialog();
            }
        });
    }
    private void createChangeNameDialog(){
        changeName = new Dialog(this);
        changeName.setContentView(R.layout.change_name_dialog);
        changeName.setTitle("Change Name");
        changeName.setCancelable(false);
        etChangeName = changeName.findViewById(R.id.etChangeName);
        bFinishChangeName = changeName.findViewById(R.id.bFinishChangeName);
        bFinishChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etChangeName.getText().toString();
                if(name.equals("")){
                    Toast.makeText(ChangeDetails.this, "שם לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", name);
                    editor.commit();
                    changeName.dismiss();
                }
            }
        });
        bCancleChangeName = changeName.findViewById(R.id.bCancleChangeName);
        bCancleChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName.dismiss();
            }
        });
        changeName.show();
    }
    private void createChangeGenderDialog()
    {
        changeGender = new Dialog(this);
        changeGender.setContentView(R.layout.change_gender_dialog);
        changeGender.setTitle("Change Gender");
        changeGender.setCancelable(false);
        rChangeChooseGender = changeGender.findViewById(R.id.rChangeChooseGender);
        bCancleChangeGender = changeGender.findViewById(R.id.bCancleChangeGender);
        bCancleChangeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGender.dismiss();
            }
        });
        bFinishChangeGender = changeGender.findViewById(R.id.bFinishChangeGender);
        bFinishChangeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = rChangeChooseGender.getCheckedRadioButtonId();
                if(id==-1)
                {
                    Toast.makeText(ChangeDetails.this, "תבחר מגדר לשנות", Toast.LENGTH_SHORT).show();
                }else {
                    gender = changeGender.findViewById(id);
                    String sGender = "";
                    if (gender.getId() == R.id.rChangeMale) {
                        sGender = "male";
                    } else if (gender.getId()== R.id.rChangeFemale) {
                        sGender = "female";
                    } else if (gender.getId()== R.id.rChangeOther) {
                        sGender = "other";
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("gender",sGender);
                    editor.commit();
                    changeGender.dismiss();
                }
            }
        });
        changeGender.show();
    }
    private void initviews() {
        bChangeGender = findViewById(R.id.bChangeGender);
        bChangeName = findViewById(R.id.bChangeName);
        sp = getSharedPreferences("details",0);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
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

}