package com.example.administrator.zhiban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.layout.simple_spinner_dropdown_item;

public class SecondActivty extends AppCompatActivity {

    private Spinner spinner;
    private Button button1;
    private  Button button2;
    private ArrayAdapter<String> spinnerAdapter;
    private int index=0;
    private Date dutydate=new Date();
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activty);
        setTitle("选择今天值班人员");

            final Intent intent=getIntent();
         String[] duty=intent.getStringArrayExtra("duty");
        spinnerAdapter=new ArrayAdapter<>(SecondActivty.this,simple_spinner_dropdown_item,duty);
        editor=getSharedPreferences("duty",MODE_PRIVATE).edit();
       // spinnerAdapter.setDropDownViewResource(simple_spinner_dropdown_item);


        spinner=(Spinner)findViewById(R.id.spinner);spinner.setPrompt("选择今天值班人员");
        spinner.setAdapter(spinnerAdapter);
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("dutyorder", index);

                editor.putString("dutydate",new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(dutydate));
                editor.apply();
                finish();
                Intent it=new Intent(SecondActivty.this,MainActivity.class);
                startActivity(it);

            }
        });
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SecondActivty.this,SettingActivity.class);
                startActivity(it);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index=position;

                Toast.makeText(SecondActivty.this, spinnerAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
