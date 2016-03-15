package com.example.zero.dianping.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.dianping.R;
import com.example.zero.dianping.activity.CityActivity;
import com.example.zero.dianping.utils.SharedUtils;

import java.io.IOException;
import java.util.List;


/**
 * Created by zero on 16-3-7.
 */
public class FragmentHome extends Fragment implements LocationListener,View.OnClickListener{
    private View view;
    private TextView topCity;
    private ImageView topScan;
    private String cityName;//当前城市
    private LocationManager locationManager;
    public static final int CITY_CODE=1;   //城市选择的标记

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index, null);

        init();

        return view;
    }

    public void init() {
        topCity = (TextView) view.findViewById(R.id.home_city);
        //获取上次定位的城市
        topCity.setText(SharedUtils.getCityName(getContext()));
        topScan = (ImageView) view.findViewById(R.id.home_scan_img);

        topCity.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_city:
                startActivityForResult(new Intent(getActivity(), CityActivity.class), CITY_CODE);
                break;
            default:break;
        }
    }
    //处理返回值
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CITY_CODE&&resultCode== Activity.RESULT_OK){
            cityName=data.getStringExtra("cityName");
            topCity.setText(cityName);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //检查GPS是否打开并定位
        checkGPSisOpen();
    }

    //接收并处理消息
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==1){
                topCity.setText(cityName);
            }
            return false;
        }
    });

    //检查GPS是否打开
    private void checkGPSisOpen() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //打开系统设置界面
        if (!isOpen) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }
        //开始定位
        startLocation();

    }

    //定位
    private void startLocation() {

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);

    }

    //获取经纬度，定位城市
    private void updateLocation(Location location) {
        double lat=0.0,lng=0.0;
        if (location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            Log.i("location","精度是："+lng+"纬度是："+lat);

            //通过经纬度获取的地址有很多个
            Geocoder geocoder=new Geocoder(getActivity());
            List<Address> list=null;
            try {
                list=geocoder.getFromLocation(lat,lng,2);
            } catch (IOException e) {
                Log.i("location","通过经纬度获取城市名称错误");;
                e.printStackTrace();
            }
            if (list!=null&&list.size()>0){
                for (int i=0;i<=list.size();i++){
                    //获取到城市
                    Address address=list.get(i);
                    cityName=address.getLocality();
                    Log.i("loction",cityName);
                }
            }else{
                cityName="无法定位";
            }
            handler.sendEmptyMessage(1);

        }else{
            cityName="无法获取当前定位经纬度";
            Log.i("location","无法获取当前经纬度");
        }
    }

    //位置更改时执行
    @Override
    public void onLocationChanged(Location location) {
            updateLocation(location);   //更新位置
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        //保存位置
        SharedUtils.putCityName(getContext(),cityName);
        //关闭定位
        stopLocation();
        super.onDestroy();
    }
    private void stopLocation(){
        locationManager.removeUpdates(this);
    }



}
