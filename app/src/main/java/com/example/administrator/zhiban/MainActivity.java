package com.example.administrator.zhiban;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private SharedPreferences geteditor;
    private String[]duty={"一","二","三","四","五","六","七"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  calendar.setSelectMore(false); //单选
        calendarCenter=(TextView) findViewById(R.id.calendarCenter);
        calendar=(CalendarView) findViewById(R.id.calendar);
        calendarLeft=(ImageButton) findViewById(R.id.calendarLeft);
        calendarRight=(ImageButton)findViewById(R.id.calendarRight);
        format = new SimpleDateFormat("yyyy-MM-dd");
        geteditor=getSharedPreferences("duty",MODE_PRIVATE);
        if (!(geteditor.getInt("dutynum",0)==0))   {
        initduty();}

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        finish();
                        Intent it=new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(it);
                        break;
                    case R.id.action_settings1:
                        Toast.makeText(MainActivity.this, "什么也没有  继续无聊吧", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings2:
                        finish();
                        break;
                    case R.id.action_settings3:
                        if (!(geteditor.getInt("dutynum",0)==0))   {
                        initduty();}
                        break;
                }
                return false;
            }
        });
        try {
            //设置日历日期
            Date date = format.parse("2015-01-01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-");
        calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("-");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                String[] ya = rightYearAndmonth.split("-");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });


//		calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {
//
//			@Override
//			public void OnItemClick(Date selectedStartDate,
//					Date selectedEndDate, Date downDate) {
//				if(calendar.isSelectMore()){
//                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate)+"到"+format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
//				}else{
//					Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
//				}
//			}
//		});




    }
    private void initduty(){
        int num=geteditor.getInt("dutynum",0);
        String dutydate=geteditor.getString("dutydate","");
        int dutyorder=geteditor.getInt("dutyorder",0);
        Date date=new Date();
        try{
            date=format.parse(dutydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] duty=new String[num];
        for (int i=0;i<num;i++){
            duty[i]=geteditor.getString("dutyname"+i,"");
            Log.d("duty", duty[i]);
        }
        calendar.setDuty(duty,date,dutyorder);
        Log.d("init", "initduty: date"+dutydate+"order"+dutyorder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

