package com.example.zero.dianping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.zero.dianping.R;
import com.example.zero.dianping.utils.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by haidaoxuiyi on 16/3/4.
 */
public class WelcomeStartActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_start);

        //Handler实现延时跳转
       /* new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);*/

        //使用java定时器Timer实现延时跳转
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (SharedUtils.getWelcomeBoolean(getBaseContext())) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getBaseContext(),WelcomeGuideAct.class));
                    SharedUtils.putWelcomeBoolean(getBaseContext(),true);
                    finish();
                }
            }


        },3000);
    }
}
