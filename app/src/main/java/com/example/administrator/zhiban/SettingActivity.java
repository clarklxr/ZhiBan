package com.example.administrator.zhiban;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private EditText Et;
    private Button SB;
    private String aa;
    private SharedPreferences.Editor editor;
    private SharedPreferences geteditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editor=getSharedPreferences("duty",MODE_PRIVATE).edit();
        geteditor=getSharedPreferences("duty",MODE_PRIVATE);
        setSupportActionBar(toolbar);
        setTitle("        输入值班人员");
       // final String ab="如果文件不为空读取人员";

        Et= (EditText)findViewById(R.id.editText);
        ETinit();
        SB=(Button)findViewById(R.id.button);
        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              aa= Et.getText().toString();

             final   String[] duty=aa.split("\n");
               // Toast.makeText(SettingActivity.this, duty[0], Toast.LENGTH_SHORT).show();

                editor.putInt("dutynum", duty.length);
                Log.d("dutynumwrite", duty.length+"");
                for (int i=0;i<duty.length;i++){
                    Log.d("a", duty[i]);
                    if(duty[i].equals("")){
                        Toast.makeText(SettingActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putString("dutyname"+i,duty[i]);
                }
                editor.apply();
                new AlertDialog.Builder(SettingActivity.this)
                       .setNegativeButton("取消",null)
                       .setMessage("确认值班人数为"+duty.length+"人")
                       .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               finish();
                               Intent it =new Intent(SettingActivity.this,SecondActivty.class);
                               it.putExtra("duty",duty);
                               startActivity(it);

                           }
                       })
                       .show();

            }
        });


    }
    private void ETinit(){
        int dutynum=geteditor.getInt("dutynum",0);
        String a="";
        Log.d("dtutynum", dutynum+"");
        if (dutynum!=0){
            for (int i=0;i<dutynum;i++){
                a+=(geteditor.getString("dutyname"+i,"")+"\n");
            }
            Et.setText(a);
        }
    }

}
