package com.example.zero.dianping.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zero.dianping.R;
import com.example.zero.dianping.fragment.FragmentHome;
import com.example.zero.dianping.fragment.FragmentMy;
import com.example.zero.dianping.fragment.FragmentSearch;
import com.example.zero.dianping.fragment.FragmentTuan;

import java.util.List;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{
    RadioGroup group;
    RadioButton main_home,main_tuan,main_search,main_my;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group=(RadioGroup)findViewById(R.id.main_bottom_tabs);
        main_home=(RadioButton)findViewById(R.id.main_home);
        main_tuan=(RadioButton)findViewById(R.id.main_tuan);
        main_search=(RadioButton)findViewById(R.id.main_search);
        main_my=(RadioButton)findViewById(R.id.main_my);
        fragmentManager=getSupportFragmentManager();


        group.setOnCheckedChangeListener(this);
        main_home.setChecked(true);

    }

    //底部按钮事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.main_home:
                fragmentChange(new FragmentHome());break;
            case R.id.main_tuan:
                fragmentChange(new FragmentTuan());break;
            case R.id.main_search:
                fragmentChange(new FragmentSearch());break;
            case R.id.main_my:
                fragmentChange(new FragmentMy());break;
            default:break;

        }
    }
    //切换fragment
    public void fragmentChange(Fragment fragment){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);

        transaction.commit();
    }
}
