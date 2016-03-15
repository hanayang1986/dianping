package com.example.zero.dianping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.zero.dianping.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 16/3/4.
 */
public class WelcomeGuideAct extends Activity{
    private ViewPager welcome_page;
    private ImageButton welcome_btn;
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_guide_act);

        welcome_page=(ViewPager)findViewById(R.id.welcome_page);
        welcome_btn=(ImageButton)findViewById(R.id.welcome_btn);
        welcome_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        list=new ArrayList<View>();
        final ImageView imageView1=new ImageView(this);
        imageView1.setImageResource(R.drawable.guide_01);
        ImageView imageView2=new ImageView(this);
        imageView2.setImageResource(R.drawable.guide_02);
        ImageView imageView3=new ImageView(this);
        imageView3.setImageResource(R.drawable.guide_03);
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);


        welcome_page.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(list.get(position));
            }
        });

        welcome_page.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    welcome_btn.setVisibility(View.VISIBLE);
                }else{
                    welcome_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
